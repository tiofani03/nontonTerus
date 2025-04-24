package id.tiooooo.nontonterus.pages.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.component.paging.PagingErrorLoadMoreView
import id.tiooooo.nontonterus.core.ui.component.paging.PagingErrorStateView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING

fun <T : Any> LazyGridScope.posterPagingView(
    items: LazyPagingItems<T>,
    spanCount: Int = 2,
    key: ((index: Int) -> Any)? = null,
    shimmerCount: Int = 6,
    shimmerItem: @Composable () -> Unit = {
        AnimatedShimmerItemView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
        )
    },
    errorContent: @Composable (String, () -> Unit) -> Unit = { message, onRetry ->
        PagingErrorStateView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            message = message,
            onRetry = onRetry
        )
    },
    loadMoreErrorContent: @Composable (String, () -> Unit) -> Unit = { message, onRetry ->
        PagingErrorLoadMoreView(
            message = message,
            onRetry = onRetry
        )
    },
    itemContent: @Composable (T) -> Unit
) {
    val loadState = items.loadState

    when (loadState.refresh) {
        is LoadState.Loading -> {
            items(shimmerCount, span = { GridItemSpan(1) }) {
                shimmerItem()
            }
        }

        is LoadState.Error -> {
            val error = (loadState.refresh as LoadState.Error).error
            item(span = { GridItemSpan(spanCount) }) {
                errorContent(error.localizedMessage ?: "Terjadi kesalahan", items::retry)
            }
        }

        else -> {
            items(
                count = items.itemCount,
                key = key,
                span = { GridItemSpan(1) }
            ) { index ->
                val data = items[index]
                data?.let { itemContent(it) }
            }

            if (loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(spanCount) }) {
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

            if (loadState.append is LoadState.Error) {
                val error = (loadState.append as LoadState.Error).error
                item(span = { GridItemSpan(spanCount) }) {
                    loadMoreErrorContent(
                        error.localizedMessage ?: "Gagal memuat lebih",
                        items::retry
                    )
                }
            }
        }
    }
}

