package id.tiooooo.nontonterus.pages.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.textMedium14
import id.tiooooo.nontonterus.core.utils.localization.stringRes

@Composable
fun MovieDetailRatingView(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    reviewCount: Int = 0,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(MEDIUM_PADDING),
            imageVector = Icons.Filled.Star,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = EXTRA_SMALL_PADDING),
            text = "$rating",
            style = textMedium14().copy(
                fontWeight = FontWeight.Bold,
            )
        )
        Text(
            modifier = Modifier.padding(start = EXTRA_SMALL_PADDING),
            text = stringRes("detail_rating_from", reviewCount),
            style = textMedium14().copy(
                fontWeight = FontWeight.Medium,
            )
        )
    }
}