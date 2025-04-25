package id.tiooooo.nontonterus.pages.detail.component.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.component.TitleLeftAndRightView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.movie.api.model.review.MovieReview

@Composable
fun MovieDetailReviewsSection(
    result: States<List<MovieReview>>,
    onSeeMoreClicked: () -> Unit = {},
    onReviewClicked: (String) -> Unit = {},
) {

    result.onLoading {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
        ) {
            repeat(6) {
                AnimatedShimmerItemView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(bottom = SMALL_PADDING)
                )
            }
        }
    }

    result.onSuccess { reviews ->
        val visibleReviews = remember(reviews) { reviews.take(3) }

        if (visibleReviews.isNotEmpty()) {
            Column {
                TitleLeftAndRightView(
                    modifier = Modifier
                        .padding(horizontal = MEDIUM_PADDING)
                        .padding(top = SMALL_PADDING)
                        .fillMaxWidth(),
                    titleLeft = stringRes("detail_reviews"),
                    isSeeMoreEnable = reviews.size > 3,
                    onSeeMoreClicked = onSeeMoreClicked
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                visibleReviews.forEach { review ->
                    MovieDetailReviewView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING)
                            .padding(bottom = SMALL_PADDING),
                        movieReview = review,
                        onReviewClicked = {
                            onReviewClicked(review.url)
                        }
                    )
                }
            }
        }
    }
}