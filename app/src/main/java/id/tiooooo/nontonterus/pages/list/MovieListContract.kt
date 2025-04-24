package id.tiooooo.nontonterus.pages.list

sealed interface MovieListEffect {
    data class NavigateToDetailMovie(val id: Long) : MovieListEffect
}

data class MovieListState(
    val type: String = "top_rated",
)

sealed interface MovieListIntent {
    data class OnTypeUpdate(val type: String) : MovieListIntent
    data class OnMovieClicked(val id: Long) : MovieListIntent
}