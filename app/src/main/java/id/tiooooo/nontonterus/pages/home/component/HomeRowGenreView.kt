package id.tiooooo.nontonterus.pages.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import id.tiooooo.nontonterus.core.network.data.States
import id.tiooooo.nontonterus.core.network.data.onError
import id.tiooooo.nontonterus.core.network.data.onLoading
import id.tiooooo.nontonterus.core.network.data.onSuccess
import id.tiooooo.nontonterus.core.ui.component.AnimatedShimmerItemView
import id.tiooooo.nontonterus.core.ui.component.TitleLeftAndRightView
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.movie.api.model.list.GenreList

@Composable
fun HomeRowGenreView(
    modifier: Modifier = Modifier,
    title: String,
    genres: States<GenreList>,
    onGenreClicked: (String, String) -> Unit = { _, _ -> },
) {
    var isShowTitle by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = modifier,
    ) {
        if (isShowTitle) {
            TitleLeftAndRightView(
                modifier = Modifier
                    .fillMaxWidth(),
                titleLeft = title,
                isSeeMoreEnable = false,
            )
        }
        genres.onLoading {
            Column(
                modifier = Modifier.padding(top = SMALL_PADDING),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
            ) {
                repeat(3) {
                    AnimatedShimmerItemView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(EXTRA_LARGE_PADDING)
                    )
                }
            }
        }
        genres.onSuccess { data ->
            FlowRow(
                modifier = Modifier.padding(top = SMALL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                verticalArrangement = Arrangement.spacedBy(EXTRA_SMALL_PADDING)
            ) {
                data.genreList.forEach { genre ->
                    Text(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(SMALL_PADDING)
                            )
                            .clickable {
                                onGenreClicked(genre.id.toString(), genre.name)
                            }
                            .padding(horizontal = SMALL_PADDING, vertical = EXTRA_SMALL_PADDING),
                        text = genre.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
        genres.onError { _, _ ->
            isShowTitle = false
        }
    }
}