package id.tiooooo.nontonterus.pages.detail.component.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.movie.api.model.video.MovieVideo

@Composable
fun MovieDetailVideoSection(
    result: States<List<MovieVideo>>,
    onVideoClicked: (String) -> Unit = {},
) {
    result.onLoading {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    result.onSuccess { videos ->
        MovieDetailVideoView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING),
            videoList = videos,
            onVideoClicked = onVideoClicked
        )
    }
}