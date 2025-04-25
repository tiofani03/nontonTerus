package id.tiooooo.nontonterus.pages.home

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieHomeScreenModel(
    private val movieRepository: MovieRepository,
) : BaseScreenModel<MovieHomeState, MovieHomeIntent, MovieHomeEffect>(MovieHomeState()) {

    init {
        dispatch(MovieHomeIntent.FetchInitialData)
        fetchSearchHistory()
    }

    override fun reducer(state: MovieHomeState, intent: MovieHomeIntent): MovieHomeState {
        return when (intent) {
            is MovieHomeIntent.UpdateMovieFilterParams -> {
                state.copy(
                    movieFilterParams = intent.params
                )
            }

            is MovieHomeIntent.UpdateSearchExpand -> {
                state.copy(
                    isSearchExpand = intent.isExpand
                )
            }

            else -> state
        }
    }

    val topRatedMovies = movieRepository
        .getAllMovieByType("top_rated")
        .cachedIn(screenModelScope)

    override suspend fun handleIntentSideEffect(intent: MovieHomeIntent) {
        when (intent) {
            is MovieHomeIntent.OnMovieClicked -> sendEffect(
                MovieHomeEffect.NavigateToDetailMovie(intent.id)
            )

            is MovieHomeIntent.OnSeeAllClicked -> sendEffect(
                MovieHomeEffect.NavigateToSeeAll(intent.type, intent.title)
            )

            is MovieHomeIntent.FetchInitialData -> {
                screenModelScope.launch {
                    movieRepository.getMovieByType("upcoming").collect { result ->
                        setState { it.copy(upComingMovies = result) }
                    }
                }
                screenModelScope.launch {
                    movieRepository.getMovieByType("now_playing").collect { result ->
                        setState { it.copy(nowPlayingMovies = result) }
                    }
                }
                screenModelScope.launch {
                    movieRepository.getMovieByType("popular").collect { result ->
                        setState { it.copy(popularMovies = result) }
                    }
                }
                screenModelScope.launch {
                    movieRepository.getGenres().collect { result ->
                        setState { it.copy(genres = result) }
                    }
                }
            }

            is MovieHomeIntent.SaveSearchQuery -> saveSearchKeyword(intent.query)
            is MovieHomeIntent.RemoveSearchQuery -> {
                screenModelScope.launch {
                    movieRepository.deleteSearchHistory(intent.searchHistoryEntity)
                }
            }
            is MovieHomeIntent.OnGenreClicked -> sendEffect(
                MovieHomeEffect.NavigateToMovieByGenre(
                    genreId = intent.genreId,
                    genreName = intent.genreName
                )
            )
            is MovieHomeIntent.OnSearchQueryClicked -> sendEffect(
                MovieHomeEffect.NavigateToSearch(intent.query)
            )

            else -> Unit
        }
    }


    private fun fetchSearchHistory() {
        screenModelScope.launch {
            movieRepository.getSearchHistory().collect { data ->
                setState {
                    state.value.copy(
                        searchHistory = data
                    )
                }
            }
        }
    }

    private fun saveSearchKeyword(keyword: String) {
        screenModelScope.launch {
            movieRepository.insertSearchHistory(
                SearchHistoryEntity(
                    query = keyword,
                )
            )
        }
    }
}