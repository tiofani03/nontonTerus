package id.tiooooo.nontonterus.core.ui.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BaseEmptyView(
    modifier: Modifier = Modifier,
    title: String = "",
    buttonText: String = "",
    @RawRes animationRes: Int? = null,
    onButtonClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            animationRes?.let {
                AnimatedPreloader(
                    modifier = Modifier
                        .size(225.dp)
                        .align(Alignment.CenterHorizontally),
                    animationRes = animationRes,
                )
            }

            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            if (buttonText.isNotEmpty()) {
                Button(
                    onClick = { onButtonClicked.invoke() },
                    modifier = Modifier
                        .defaultMinSize(minWidth = 150.dp)
                ) {
                    Text(text = buttonText)
                }
            }
        }
    }
}