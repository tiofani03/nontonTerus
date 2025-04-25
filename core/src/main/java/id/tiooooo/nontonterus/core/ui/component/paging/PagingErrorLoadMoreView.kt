package id.tiooooo.nontonterus.core.ui.component.paging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.localization.stringRes

@Composable
fun PagingErrorLoadMoreView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(EXTRA_SMALL_PADDING))
        OutlinedButton(onClick = onRetry) {
            Text(stringRes("try_again"))
        }
    }
}