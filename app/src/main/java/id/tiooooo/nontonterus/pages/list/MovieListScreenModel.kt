package id.tiooooo.nontonterus.pages.list

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

class MovieListScreenModel(
    private val movieRepository: MovieRepository,
) :
    BaseScreenModel<MovieListState, MovieListIntent, MovieListEffect>(MovieListState()) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = state
        .flatMapLatest {
            if (it.query.isNotEmpty()) movieRepository.getMovieByQuery(it.query)
            else if (it.genreId.isNotEmpty()) movieRepository.getDiscoverMovie(it.genreId)
            else movieRepository.getAllMovieByType(it.type)
        }.cachedIn(screenModelScope)

    override fun reducer(state: MovieListState, intent: MovieListIntent): MovieListState {
        return when (intent) {
            is MovieListIntent.OnArgumentUpdated -> {
                state.copy(
                    type = intent.type,
                    query = intent.query,
                    genreId = intent.genreId,
                )
            }

            else -> state
        }
    }

    override suspend fun handleIntentSideEffect(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.OnMovieClicked -> {
                sendEffect(MovieListEffect.NavigateToDetailMovie(intent.id))
            }

            else -> Unit
        }
    }
}