package id.tiooooo.nontonterus.core.ui.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import id.tiooooo.nontonterus.core.ui.theme.IMAGE_HOME_SIZE
import id.tiooooo.nontonterus.core.ui.theme.LARGE_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING

@Composable
fun AnimatedShimmerItemView(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LARGE_PADDING,
    shimmerHeight: Dp = IMAGE_HOME_SIZE,
) {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    ShimmerItemView(
        modifier = modifier,
        alpha = alphaAnim,
        cornerRadius = cornerRadius,
        shimmerHeight = shimmerHeight,
    )
}

@Composable
fun ShimmerItemView(
    alpha: Float,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = LARGE_PADDING,
    shimmerHeight: Dp,
) {
    val baseColor = MaterialTheme.colorScheme.surfaceVariant
    val highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)

    Surface(
        modifier = modifier
            .alpha(alpha)
            .fillMaxWidth()
            .height(shimmerHeight),
        color = highlightColor,
        shape = RoundedCornerShape(cornerRadius)
    ) {}
}
