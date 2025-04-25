package id.tiooooo.nontonterus.pages.list

sealed interface MovieListEffect {
    data class NavigateToDetailMovie(val id: Long) : MovieListEffect
}

data class MovieListState(
    val type: String = "",
    val query: String = "",
    val genreId: String = "",
)

sealed interface MovieListIntent {
    data class OnArgumentUpdated(val type: String, val query: String, val genreId: String) :
        MovieListIntent

    data class OnMovieClicked(val id: Long) : MovieListIntent
}