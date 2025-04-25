package id.tiooooo.nontonterus.pages.splash

sealed interface SplashEffect {
    data object NavigateToHome : SplashEffect
}

data class SplashState(
    val isLoading: Boolean = true,
)

sealed interface SplashIntent {
    data object NavigateToHome : SplashIntent
}

