package id.tiooooo.nontonterus.pages.home

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.core.utils.localization.LocalizationManager
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MovieHomeScreenModel(
    private val movieRepository: MovieRepository,
    private val appDatastore: AppDatastore,
    val localizationManager: LocalizationManager,
) : BaseScreenModel<MovieHomeState, MovieHomeIntent, MovieHomeEffect>(MovieHomeState()) {

    init {
        dispatch(MovieHomeIntent.FetchInitialData)
        fetchSearchHistory()
        initLocalization()
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

            is MovieHomeIntent.UpdateIsRefreshing -> state.copy(isRefreshing = intent.isRefreshing)
            is MovieHomeIntent.OnOpenDialogSetting -> state.copy(
                isOpenDialogSetting = intent.isOpen
            )
            is MovieHomeIntent.UpdateShowTopRated -> state.copy(
                isShowTopRated = intent.value
            )

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
                    launch {
                        movieRepository.getMovieByType("upcoming")
                            .collect { result ->
                                setState { it.copy(upComingMovies = result, isShowTopRated = true) }
                            }
                    }

                    launch {
                        movieRepository.getMovieByType("now_playing")
                            .collect { result ->
                                setState { it.copy(nowPlayingMovies = result) }
                            }
                    }

                    launch {
                        movieRepository.getMovieByType("popular")
                            .collect { result ->
                                setState { it.copy(popularMovies = result) }
                            }
                    }

                    launch {
                        movieRepository.getGenres()
                            .collect { result ->
                                setState { it.copy(genres = result) }
                            }
                    }

                    delay(100)
                    setState { it.copy(isRefreshing = false) }
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

            is MovieHomeIntent.UpdateTheme -> {
                appDatastore.setActiveTheme(intent.value)
                setState {
                    it.copy(
                        activeTheme = intent.value
                    )
                }
            }

            is MovieHomeIntent.UpdateLanguage -> {
                appDatastore.setSelectedLanguage(intent.value)
                localizationManager.loadLanguage(intent.value)
                setState {
                    it.copy(
                        selectedLanguage = intent.value
                    )
                }
            }

            else -> Unit
        }
    }

    private fun initLocalization() {
        screenModelScope.launch {
            val selectedLanguage = appDatastore.selectedLanguage.first()
            val selectedTheme = appDatastore.activeTheme.first()
            localizationManager.loadLanguage(selectedLanguage)
            setState {
                it.copy(
                    selectedLanguage = selectedLanguage,
                    activeTheme = selectedTheme,
                )
            }
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