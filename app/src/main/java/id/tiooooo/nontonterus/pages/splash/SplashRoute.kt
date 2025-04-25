package id.tiooooo.nontonterus.pages.splash

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import id.tiooooo.nontonterus.core.ui.theme.primaryDark
import id.tiooooo.nontonterus.core.ui.theme.primaryLight

class SplashRoute : Screen {
    @Composable
    override fun Content() {
        val splashScreenModel = koinScreenModel<SplashScreenModel>()
        val splashModifier =
            Modifier.background(Brush.verticalGradient(listOf(primaryDark, primaryLight)))
        SplashScreen(
            modifier = splashModifier,
            screenModel = splashScreenModel,
        )
    }
}