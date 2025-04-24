package id.tiooooo.nontonterus.pages.review

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class ReviewRoute(
    private val movieId: Long,
    private val movieName: String,
) : Screen {
    @Composable
    override fun Content() {
        ReviewScreen(
            modifier = Modifier.fillMaxSize(),
            movieId = movieId,
            movieName = movieName,
            screenModel = koinScreenModel()
        )
    }
}