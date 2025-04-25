package id.tiooooo.nontonterus.pages.list

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class MovieListScreenModel(
    private val movieRepository: MovieRepository,
) :
    BaseScreenModel<MovieListState, MovieListIntent, MovieListEffect>(MovieListState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = state
        .map { it.movieFilterState }
        .distinctUntilChanged()
        .flatMapLatest {
            if (it.query.isNotEmpty()) movieRepository.getMovieByQuery(it.query)
            else if (it.genreId.isNotEmpty()) movieRepository.getDiscoverMovie(it.genreId)
            else movieRepository.getAllMovieByType(it.type)
        }.cachedIn(screenModelScope)

    override fun reducer(state: MovieListState, intent: MovieListIntent): MovieListState {
        return when (intent) {
            is MovieListIntent.OnArgumentUpdated -> {
                state.copy(
                    movieFilterState = MovieFilterState(
                        type = intent.type,
                        query = intent.query,
                        genreId = intent.genreId,
                    )
                )
            }

            is MovieListIntent.UpdateIsRefreshing -> {
                state.copy(isRefreshing = intent.isRefreshing)
            }

            else -> state
        }
    }

    override suspend fun handleIntentSideEffect(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.OnMovieClicked -> {
                sendEffect(MovieListEffect.NavigateToDetailMovie(intent.id))
            }

            is MovieListIntent.UpdateIsRefreshing -> {
                delay(500)
                setState { it.copy(isRefreshing = false) }
            }

            else -> Unit
        }
    }
}