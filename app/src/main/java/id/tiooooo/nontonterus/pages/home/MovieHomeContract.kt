package id.tiooooo.nontonterus.pages.home

import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult

sealed interface MovieHomeEffect {
    data class NavigateToDetailMovie(val id: Long) : MovieHomeEffect
    data class NavigateToSeeAll(val type: String, val title: String) : MovieHomeEffect
}

data class MovieHomeState(
    val nowPlayingMovies: States<List<MovieResult>> = States.Loading,
    val popularMovies: States<List<MovieResult>> = States.Loading,
    val upComingMovies: States<List<MovieResult>> = States.Loading,
)

sealed interface MovieHomeIntent {
    data object FetchInitialData : MovieHomeIntent
    data class OnMovieClicked(val id: Long) : MovieHomeIntent
    data class OnSeeAllClicked(val type: String, val title: String) : MovieHomeIntent
}