package id.tiooooo.nontonterus.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.textMedium18

@Composable
fun TitleLeftAndRightView(
    modifier: Modifier = Modifier,
    titleLeft: String,
    isSeeMoreEnable: Boolean = false,
    onSeeMoreClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = titleLeft,
            style = textMedium18().copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        if (isSeeMoreEnable) {
            Icon(
                modifier = Modifier
                    .padding(vertical = EXTRA_SMALL_PADDING)
                    .clickable { onSeeMoreClicked.invoke() },
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
            )
        }
    }
}
