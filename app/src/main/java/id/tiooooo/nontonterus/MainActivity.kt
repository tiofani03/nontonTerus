package id.tiooooo.nontonterus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import id.tiooooo.nontonterus.core.ui.theme.NontonTerusTheme
import id.tiooooo.nontonterus.pages.home.MovieHomeRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NontonTerusTheme {
                Navigator(
                    screen = MovieHomeRoute(),
                    disposeBehavior = NavigatorDisposeBehavior(),
                    onBackPressed = { true },
                ) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}