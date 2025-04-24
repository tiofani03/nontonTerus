package id.tiooooo.nontonterus.pages.detail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING

@Composable
fun MovieDetailOverviewSection(overview: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING),
        text = overview
    )
}