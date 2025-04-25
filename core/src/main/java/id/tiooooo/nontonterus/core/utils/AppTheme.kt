package id.tiooooo.nontonterus.core.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import kotlinx.coroutines.flow.map

enum class AppTheme(val label: String) {
    SYSTEM("theme_system"),
    LIGHT("theme_light_mode"),
    DARK("theme_dark_mode");

    companion object {
        fun fromValue(value: String): AppTheme {
            return entries.find { it.label == value } ?: SYSTEM
        }
    }

    fun toDarkMode(): Boolean? = when (this) {
        SYSTEM -> null
        LIGHT -> false
        DARK -> true
    }
}


@Composable
fun rememberAppTheme(appDatastore: AppDatastore): Boolean {
    val appTheme by appDatastore.activeTheme
        .map { AppTheme.fromValue(it) }
        .collectAsState(initial = AppTheme.SYSTEM)

    return when (appTheme.toDarkMode()) {
        true -> true
        false -> false
        null -> isSystemInDarkTheme()
    }
}

@Composable
fun SetupStatusBarAppearance(darkTheme: Boolean) {
    val view = LocalView.current
    SideEffect {
        ViewCompat.getWindowInsetsController(view)?.let { controller ->
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }
}
