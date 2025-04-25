package id.tiooooo.nontonterus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import id.tiooooo.nontonterus.core.ui.theme.NontonTerusTheme
import id.tiooooo.nontonterus.core.utils.localization.ProvideLocalization
import id.tiooooo.nontonterus.core.utils.localization.rememberLocalization
import id.tiooooo.nontonterus.core.utils.rememberAppTheme
import id.tiooooo.nontonterus.pages.home.MovieHomeRoute
import id.tiooooo.nontonterus.pages.splash.SplashRoute
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appDatastore: AppDatastore by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = rememberAppTheme(appDatastore)
            val localizationProvider = rememberLocalization(appDatastore)
            ProvideLocalization(localizationProvider) {
                NontonTerusTheme(darkTheme) {
                    Navigator(
                        screen = SplashRoute(),
                        disposeBehavior = NavigatorDisposeBehavior(),
                        onBackPressed = { true },
                    ) { navigator ->
                        SlideTransition(navigator = navigator)
                    }
                }
            }
        }
    }
}