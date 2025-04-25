package id.tiooooo.nontonterus.pages.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onError
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.component.TitleLeftAndRightView
import id.tiooooo.nontonterus.core.ui.theme.IMAGE_HOME_SIZE
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.movie.api.model.list.MovieResult

@Composable
fun HomeRowMovieView(
    modifier: Modifier = Modifier,
    title: String,
    movies: States<List<MovieResult>>,
    onSeeMoreClicked: () -> Unit,
    onMovieClicked: (MovieResult) -> Unit,
) {
    var isShowTitle by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = modifier,
    ) {
        if (isShowTitle){
            TitleLeftAndRightView(
                modifier = Modifier
                    .fillMaxWidth(),
                titleLeft = title,
                isSeeMoreEnable = true,
                onSeeMoreClicked = onSeeMoreClicked
            )
        }
        movies.onLoading {
            LazyRow(
                modifier = Modifier.padding(top = SMALL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {
                items(5) {
                    AnimatedShimmerItemView(
                        modifier = Modifier
                            .width(150.dp)
                            .height(IMAGE_HOME_SIZE)
                    )
                }
            }
        }

        movies.onSuccess { data ->
            LazyRow(
                modifier = Modifier.padding(top = SMALL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {
                items(data, key = { it.id }) { movie ->
                    HomePosterView(
                        modifier = Modifier
                            .width(150.dp)
                            .aspectRatio(2f / 3f)
                            .clickable { onMovieClicked.invoke(movie) },
                        movie = movie,
                    )
                }
            }
        }
        movies.onError { _, _ ->
            isShowTitle = false
        }
    }
}