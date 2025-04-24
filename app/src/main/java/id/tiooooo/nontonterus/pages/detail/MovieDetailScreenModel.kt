package id.tiooooo.nontonterus.pages.detail

import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailScreenModel(
    private val movieRepository: MovieRepository,
) : BaseScreenModel<MovieDetailState, MovieDetailIntent, MovieDetailEffect>(MovieDetailState()) {
    override fun reducer(state: MovieDetailState, intent: MovieDetailIntent): MovieDetailState {
        return state
    }

    override suspend fun handleIntentSideEffect(intent: MovieDetailIntent) {
        when(intent){
            is MovieDetailIntent.FetchMovieDetail -> {
                screenModelScope.launch {
                    movieRepository.getDetailMovie(intent.id.toString()).collect { result ->
                        setState { it.copy(movieDetail = result) }
                    }
                }

                screenModelScope.launch {
                    movieRepository.getMovieVideos(intent.id.toString()).collect { result ->
                        setState { it.copy(movieVideos = result) }
                    }
                }

                screenModelScope.launch {
                    movieRepository.getMovieReviews(intent.id.toString()).collect { result ->
                        setState { it.copy(movieReviews = result) }
                    }
                }
            }

            is MovieDetailIntent.OnReviewSeeMoreClick -> {
                sendEffect(
                    MovieDetailEffect.NavigateToReviewSeeMore(
                        intent.id,
                        intent.movieName
                    )
                )
            }

            is MovieDetailIntent.OnReviewDetailClick -> {
                sendEffect(
                    MovieDetailEffect.NavigateToReviewDetail(
                        intent.url,
                        intent.movieName
                    )
                )
            }

            is MovieDetailIntent.OnVideoClick -> {
                sendEffect(
                    MovieDetailEffect.NavigateToVideo(
                        intent.key
                    )
                )
            }
        }
    }
}