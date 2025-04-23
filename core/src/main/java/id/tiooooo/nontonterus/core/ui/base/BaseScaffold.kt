package id.tiooooo.nontonterus.core.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    bottomBar: (@Composable () -> Unit)? = null,
    topBar: (@Composable () -> Unit)? = null,
    snackBarHostState: (@Composable () -> Unit)? = null,
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            bottomBar?.invoke()
        },
        topBar = { topBar?.invoke() },
        snackbarHost = { snackBarHostState?.invoke() }
    ) { padding ->
        content(padding)
    }
}