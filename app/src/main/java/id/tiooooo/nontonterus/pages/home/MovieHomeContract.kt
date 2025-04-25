package id.tiooooo.nontonterus.pages.home

import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.movie.api.model.list.GenreList
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult

sealed interface MovieHomeEffect {
    data class NavigateToDetailMovie(val id: Long) : MovieHomeEffect
    data class NavigateToSeeAll(val type: String, val title: String) : MovieHomeEffect
    data class NavigateToMovieByGenre(val genreId: String, val genreName: String) : MovieHomeEffect
    data class NavigateToSearch(val query: String) : MovieHomeEffect
}

data class MovieHomeState(
    val nowPlayingMovies: States<List<MovieResult>> = States.Loading,
    val popularMovies: States<List<MovieResult>> = States.Loading,
    val upComingMovies: States<List<MovieResult>> = States.Loading,
    val genres: States<GenreList> = States.Loading,
    val movieFilterParams: MovieFilterParams = MovieFilterParams(),
    val searchHistory: List<SearchHistoryEntity> = listOf(),
    val isSearchExpand: Boolean = false,
)

sealed interface MovieHomeIntent {
    data object FetchInitialData : MovieHomeIntent
    data class OnMovieClicked(val id: Long) : MovieHomeIntent
    data class OnSeeAllClicked(val type: String, val title: String) : MovieHomeIntent
    data class OnGenreClicked(val genreId: String, val genreName: String) : MovieHomeIntent
    data class OnSearchQueryClicked(val query: String) : MovieHomeIntent
    data class UpdateMovieFilterParams(val params: MovieFilterParams) : MovieHomeIntent
    data class UpdateSearchExpand(val isExpand: Boolean) : MovieHomeIntent
    data class SaveSearchQuery(val query: String) : MovieHomeIntent
    data class RemoveSearchQuery(val searchHistoryEntity: SearchHistoryEntity) : MovieHomeIntent
}

data class MovieFilterParams(
    val query: String = "",
)