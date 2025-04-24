package id.tiooooo.nontonterus.pages.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MovieDetailRoute(
    private val movieId: Long
) : Screen {
    @Composable
    override fun Content() {
        MovieDetailScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel(),
            movieId = movieId,
        )
    }
}