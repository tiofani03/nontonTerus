package id.tiooooo.nontonterus.pages.videoplayer

import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold

class VideoPlayerScreen(val videoId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var fullscreenView by remember { mutableStateOf<View?>(null) }
        var isFullscreen by remember { mutableStateOf(false) }

        if (isFullscreen && fullscreenView != null) {
            FullscreenVideoScreen(
                fullscreenView = fullscreenView!!,
                onExitFullscreen = {
                    isFullscreen = false
                    fullscreenView = null
                    navigator.pop()
                }
            )
        } else {
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

                            addFullscreenListener(object : FullscreenListener {
                                override fun onEnterFullscreen(
                                    view: View,
                                    exitFullscreen: () -> Unit
                                ) {
                                    fullscreenView = view
                                    isFullscreen = true
                                }

                                override fun onExitFullscreen() {
                                    fullscreenView = null
                                    isFullscreen = false
                                }
                            })

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
}

@Composable
fun FullscreenVideoScreen(
    fullscreenView: View,
    onExitFullscreen: () -> Unit
) {
    BackHandler {
        onExitFullscreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { fullscreenView },
            modifier = Modifier.fillMaxSize()
        )
    }
}
