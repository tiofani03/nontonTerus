package id.tiooooo.nontonterus.pages.detail

import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.movie.api.model.detail.MovieDetail
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo


data class MovieDetailState(
    val movieDetail: States<MovieDetail> = States.Loading,
    val movieReviews: States<List<MovieReview>> = States.Loading,
    val movieVideos: States<List<MovieVideo>> = States.Loading,
)

sealed interface MovieDetailIntent {
    data class FetchMovieDetail(val id: Long) : MovieDetailIntent
    data class OnReviewSeeMoreClick(val id: Long, val movieName: String) : MovieDetailIntent
    data class OnReviewDetailClick(val url: String, val movieName: String) : MovieDetailIntent
    data class OnVideoClick(val key: String) : MovieDetailIntent
}

sealed interface MovieDetailEffect {
    data class NavigateToReviewSeeMore(val id: Long, val movieName: String) : MovieDetailEffect
    data class NavigateToReviewDetail(val url: String, val movieName: String) : MovieDetailEffect
    data class NavigateToVideo(val key: String) : MovieDetailEffect
}