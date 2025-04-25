package id.tiooooo.nontonterus.pages.splash

import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import id.tiooooo.nontonterus.core.ui.base.BaseScreenModel
import id.tiooooo.nontonterus.core.utils.localization.LocalizationManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SplashScreenModel(
    private val appDatastore: AppDatastore,
    private val localizationManager: LocalizationManager,
) : BaseScreenModel<SplashState, SplashIntent, SplashEffect>(
    initialState = SplashState()
) {
    init {
        screenModelScope.launch {
            appDatastore.selectedLanguage
                .map { it.ifEmpty { "en" } }
                .distinctUntilChanged()
                .collectLatest { lang ->
                    localizationManager.loadLanguage(lang)
                }
        }
    }

    override fun reducer(state: SplashState, intent: SplashIntent): SplashState {
        return when (intent) {
            is SplashIntent.NavigateToHome -> state.copy(isLoading = true)
        }
    }

    override suspend fun handleIntentSideEffect(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.NavigateToHome -> {
                screenModelScope.launch {
                    delay(1500)
                    sendEffect(SplashEffect.NavigateToHome)
                }
            }
        }
    }
}

