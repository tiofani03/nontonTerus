package id.tiooooo.nontonterus.pages.detail.component.video

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo

@Composable
fun MovieDetailVideoView(
    modifier: Modifier = Modifier,
    videoList: List<MovieVideo> = emptyList(),
    onVideoClicked: (String) -> Unit = {},
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(videoList, key = { it.id }) { item ->
            MovieVideoItem(
                video = item,
                onClick = { onVideoClicked.invoke(item.key) }
            )
        }
    }
}

@Composable
fun MovieVideoItem(
    video: MovieVideo,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(200.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(SMALL_PADDING))
            .background(Color.DarkGray)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(video.createThumbnailUrl())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            },
            error = {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        )

        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play Video",
            tint = Color.White
        )
    }
}
