package id.tiooooo.nontonterus.pages.detail

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
import org.koin.android.ext.android.inject

class DetailActivity : ComponentActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private val appDatastore: AppDatastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = intent.getLongExtra(EXTRA_ID, 0L)
        setContent {
            val darkTheme = rememberAppTheme(appDatastore)
            val localizationProvider = rememberLocalization(appDatastore)
            ProvideLocalization(localizationProvider) {
                NontonTerusTheme(darkTheme) {
                    Navigator(
                        screen = MovieDetailRoute(id),
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