package id.tiooooo.nontonterus.pages.detail.component.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.textMedium12
import id.tiooooo.nontonterus.core.ui.theme.textMedium14
import id.tiooooo.nontonterus.core.ui.theme.textMedium16
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview

@Composable
fun MovieDetailReviewView(
    modifier: Modifier = Modifier,
    movieReview: MovieReview,
    onReviewClicked: (String) -> Unit = {},
) {
    val imageUrl = remember(movieReview.authorAva) { movieReview.authorAva }
    val context = LocalContext.current
    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }
    Card(
        modifier = modifier,
        onClick = {
            onReviewClicked.invoke(movieReview.url)
        }
    ) {
        Column(
            modifier = Modifier.padding(MEDIUM_PADDING)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    model = imageRequest,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    loading = {
                        AnimatedShimmerItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                    },
                    error = {
                        AnimatedShimmerItemView(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(start = SMALL_PADDING)
                        .weight(1f),
                ) {
                    Text(
                        text = movieReview.author,
                        style = textMedium16().copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = movieReview.showCreatedAt(),
                        style = textMedium12().copy(
                            fontWeight = FontWeight.Light,
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(0.3f),
                            shape = RoundedCornerShape(SMALL_PADDING)
                        )
                        .padding(EXTRA_SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(MEDIUM_PADDING),
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
                    Text(
                        text = movieReview.rating,
                        style = textMedium12(),
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            Text(
                text = movieReview.content,
                style = textMedium14().copy(
                    fontWeight = FontWeight.Light
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}