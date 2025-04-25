package id.tiooooo.nontonterus.pages.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MovieListRoute(
    val type: String = "",
    val genreId: String = "",
    val query: String = "",
    val title: String,
) : Screen {

    @Composable
    override fun Content() {
        MovieListScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel(),
            type = type,
            genreId = genreId,
            query = query,
            title = title,
        )
    }
}