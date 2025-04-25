package id.tiooooo.nontonterus.pages.detail.component.video

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo

@Composable
fun MovieDetailVideoSection(
    result: States<List<MovieVideo>>,
    onVideoClicked: (String) -> Unit = {},
) {
    result.onLoading {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
        ) {
            repeat(3) {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .width(200.dp)
                        .height(120.dp)
                        .padding(horizontal = SMALL_PADDING)
                )
            }
        }
    }

    result.onSuccess { videos ->
        MovieDetailVideoView(
            modifier = Modifier
                .fillMaxWidth(),
            videoList = videos,
            onVideoClicked = onVideoClicked
        )
    }
}