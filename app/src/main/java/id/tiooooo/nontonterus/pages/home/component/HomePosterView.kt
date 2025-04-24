package id.tiooooo.nontonterus.pages.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.movie.api.model.detail.createImageUrl
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult

@Composable
fun HomePosterView(
    modifier: Modifier = Modifier,
    movie: MovieResult,
) {
    val imageUrl = remember(movie.posterPath) {
        movie.posterPath.createImageUrl(false)
    }
    val title = remember(movie.title) { movie.title }
    val context = LocalContext.current
    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(SMALL_PADDING))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageRequest,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            loading = {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        )
    }
}