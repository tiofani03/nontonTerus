package id.tiooooo.nontonterus.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MovieHomeRoute : Screen {
    @Composable
    override fun Content() {
        MovieHomeScreen(
            modifier = Modifier.fillMaxSize(),
            screenModel = koinScreenModel(),
        )
    }
}