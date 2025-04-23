package id.tiooooo.nontonterus.core.ui.base

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseScreenModel<STATE, INTENT, EFFECT>(
    initialState: STATE
) : ScreenModel {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    protected fun setState(reducer: (STATE) -> STATE) {
        _state.update(reducer)
    }

    protected suspend fun sendEffect(effect: EFFECT) {
        _effect.send(effect)
    }

    abstract fun reducer(state: STATE, intent: INTENT): STATE
    abstract suspend fun handleIntentSideEffect(intent: INTENT)

    fun dispatch(intent: INTENT) {
        setState { reducer(it, intent) }
        screenModelScope.launch { handleIntentSideEffect(intent) }
    }
}