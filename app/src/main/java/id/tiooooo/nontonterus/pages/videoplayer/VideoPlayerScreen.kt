package id.tiooooo.nontonterus.pages.videoplayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold

class VideoPlayerScreen(
    private val videoId: String
) : Screen {

    @Composable
    override fun Content() {
        BaseScaffold {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    YouTubePlayerView(context).apply {
                        val options = IFramePlayerOptions.Builder()
                            .controls(1)
                            .fullscreen(1)
                            .build()

                        enableAutomaticInitialization = false

                        initialize(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        }, options)
                    }
                }
            )
        }
    }
}