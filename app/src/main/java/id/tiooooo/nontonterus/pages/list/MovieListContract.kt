package id.tiooooo.nontonterus.pages.list

sealed interface MovieListEffect {
    data class NavigateToDetailMovie(val id: Long) : MovieListEffect
}

data class MovieListState(
    val movieFilterState: MovieFilterState = MovieFilterState(),
    val isRefreshing: Boolean = false,
)

sealed interface MovieListIntent {
    data class OnArgumentUpdated(val type: String, val query: String, val genreId: String) :
        MovieListIntent

    data class OnMovieClicked(val id: Long) : MovieListIntent
    data class UpdateIsRefreshing(val isRefreshing: Boolean) : MovieListIntent
}

data class MovieFilterState(
    val query: String = "",
    val genreId: String = "",
    val type: String = "popular"
)