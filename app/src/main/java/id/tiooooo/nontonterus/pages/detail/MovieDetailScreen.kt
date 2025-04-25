package id.tiooooo.nontonterus.pages.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onError
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.AnimatedPreloader
import id.tiooooo.nontonterus.core.ui.component.BaseEmptyView
import id.tiooooo.nontonterus.core.ui.component.BasicTopBarTitleView
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_EXTRA_HUGE_PADDING
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.core.utils.pushOnce
import id.tiooooo.nontonterus.pages.detail.component.MovieDetailImageView
import id.tiooooo.nontonterus.pages.detail.component.MovieDetailOverviewSection
import id.tiooooo.nontonterus.pages.detail.component.MovieDetailRatingView
import id.tiooooo.nontonterus.pages.detail.component.MovieDetailTitleView
import id.tiooooo.nontonterus.pages.detail.component.review.MovieDetailReviewsSection
import id.tiooooo.nontonterus.pages.detail.component.video.MovieDetailVideoSection
import id.tiooooo.nontonterus.pages.review.DetailReviewScreen
import id.tiooooo.nontonterus.pages.review.ReviewRoute
import id.tiooooo.nontonterus.pages.videoplayer.VideoPlayerScreen

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieId: Long,
    screenModel: MovieDetailScreenModel
) {
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(movieId) {
        if (screenModel.state.value.movieDetail !is States.Success) {
            screenModel.dispatch(MovieDetailIntent.FetchMovieDetail(movieId))
        }
    }

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is MovieDetailEffect.NavigateToReviewSeeMore -> {
                    navigator.pushOnce(
                        ReviewRoute(
                            movieId = effect.id,
                            movieName = effect.movieName,
                        )
                    )
                }

                is MovieDetailEffect.NavigateToReviewDetail -> {
                    navigator.pushOnce(
                        DetailReviewScreen(
                            webViewUrl = effect.url,
                            movieName = effect.movieName,
                        )
                    )
                }

                is MovieDetailEffect.NavigateToVideo -> {
                    navigator.pushOnce(
                        VideoPlayerScreen(
                            videoId = effect.key,
                        )
                    )
                }
            }
        }
    }

    val state by screenModel.state.collectAsState()
    val movieDetailResult = state.movieDetail
    val movieVideosResult = state.movieVideos
    val movieReviewsResult = state.movieReviews

    movieDetailResult.onLoading {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AnimatedPreloader(
                modifier = Modifier
                    .size(225.dp)
                    .align(Alignment.Center),
                animationRes = id.tiooooo.nontonterus.core.R.raw.lottie_loading,
            )
        }
    }

    movieDetailResult.onSuccess { movieResult ->
        val movie = remember(movieResult) { movieResult }

        BaseScaffold { paddingValues ->
            LazyColumn(
                modifier = modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                contentPadding = PaddingValues(bottom = EXTRA_EXTRA_HUGE_PADDING),
                verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)
            ) {
                item {
                    MovieDetailImageView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        image = movie.createBackdropPath(),
                    )
                }

                item {
                    MovieDetailRatingView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING),
                        rating = movie.voteAverage,
                        reviewCount = movie.voteCount,
                    )
                }

                item {
                    MovieDetailTitleView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING),
                        title = movie.createTitleWithYear(),
                        genres = movie.genres,
                    )
                }

                item {
                    MovieDetailVideoSection(
                        result = movieVideosResult,
                        onVideoClicked = {
                            screenModel.dispatch(
                                MovieDetailIntent.OnVideoClick(
                                    key = it,
                                )
                            )
                        }
                    )
                }

                item { MovieDetailOverviewSection(movie.overview) }

                item {
                    MovieDetailReviewsSection(
                        result = movieReviewsResult,
                        onSeeMoreClicked = {
                            screenModel.dispatch(
                                MovieDetailIntent.OnReviewSeeMoreClick(
                                    id = movieId,
                                    movieName = movie.title,
                                )
                            )
                        },
                        onReviewClicked = {
                            screenModel.dispatch(
                                MovieDetailIntent.OnReviewDetailClick(
                                    url = it,
                                    movieName = movie.title,
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    movieDetailResult.onError { message, _ ->
        BaseScaffold(
            topBar = {
                BasicTopBarTitleView(
                    title = stringRes("detail_title"),
                    onBackClicked = { navigator.pop() }
                )
            }
        ) {
            BaseEmptyView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING),
                title = message,
                animationRes = id.tiooooo.nontonterus.core.R.raw.lottie_popcorn,
                buttonText = stringRes("try_again"),
                onButtonClicked = {
                    screenModel.dispatch(MovieDetailIntent.FetchMovieDetail(movieId))
                },
            )
        }
    }
}