package id.tiooooo.nontonterus.pages.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.component.BasicTopBarTitleView
import id.tiooooo.nontonterus.core.ui.component.paging.PagingErrorLoadMoreView
import id.tiooooo.nontonterus.core.ui.component.paging.PagingErrorStateView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.core.utils.pushOnce
import id.tiooooo.nontonterus.pages.detail.component.review.MovieDetailReviewView

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    movieName: String,
    movieId: Long,
    screenModel: ReviewScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(movieId) {
        screenModel.setMovieId(movieId)
    }

    val reviews = screenModel.movieReview.collectAsLazyPagingItems()

    BaseScaffold(
        modifier = modifier,
        topBar = {
            BasicTopBarTitleView(
                title = movieName,
                onBackClicked = { navigator.pop() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            when (reviews.loadState.refresh) {
                is LoadState.Loading -> {
                    items(6) {
                        AnimatedShimmerItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(bottom = SMALL_PADDING)

                        )
                    }
                }

                is LoadState.Error -> {
                    val error = (reviews.loadState.refresh as LoadState.Error).error
                    item {
                        PagingErrorStateView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MEDIUM_PADDING),
                            message = error.localizedMessage ?: "Terjadi kesalahan",
                            onRetry = { reviews.retry() }
                        )
                    }
                }

                else -> {
                    items(reviews.itemCount) { index ->
                        val review = reviews[index]
                        review?.let { item ->
                            MovieDetailReviewView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MEDIUM_PADDING)
                                    .padding(bottom = SMALL_PADDING),
                                movieReview = item,
                                onReviewClicked = {
                                    navigator.pushOnce(
                                        DetailReviewScreen(
                                            webViewUrl = item.url,
                                            movieName = movieName,
                                        )
                                    )
                                }
                            )
                        }
                    }

                    if (reviews.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SMALL_PADDING),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    if (reviews.loadState.append is LoadState.Error) {
                        val error = (reviews.loadState.append as LoadState.Error).error
                        item {
                            PagingErrorLoadMoreView(
                                message = stringRes("failed_to_load_data"),
                                onRetry = { reviews.retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}