package id.tiooooo.nontonterus.pages.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.textMedium24

@Composable
fun MovieDetailTitleView(
    modifier: Modifier = Modifier,
    title: String,
    genres: List<String> = emptyList(),
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = textMedium24().copy(
                fontWeight = FontWeight.Bold,
            )
        )
        if (genres.isNotEmpty()) {
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING)
            ) {
                items(genres) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(SMALL_PADDING)
                            )
                            .padding(horizontal = SMALL_PADDING, vertical = EXTRA_SMALL_PADDING),
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}