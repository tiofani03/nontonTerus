package id.tiooooo.nontonterus.pages.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.BasicTopBarTitleView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.pushOnce
import id.tiooooo.nontonterus.pages.detail.MovieDetailRoute
import id.tiooooo.nontonterus.pages.home.component.HomePosterView
import id.tiooooo.nontonterus.pages.home.component.posterPagingView

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    screenModel: MovieListScreenModel,
    title: String,
    genreId: String,
    query: String,
    type: String,
) {
    val navigator = LocalNavigator.currentOrThrow
    val movies = screenModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(type) {
        screenModel.dispatch(
            MovieListIntent.OnArgumentUpdated(
                type = type,
                genreId = genreId,
                query = query,
            )
        )
    }

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is MovieListEffect.NavigateToDetailMovie -> {
                    navigator.pushOnce(
                        MovieDetailRoute(movieId = effect.id)
                    )
                }
            }
        }
    }

    BaseScaffold(
        modifier = modifier,
        topBar = {
            BasicTopBarTitleView(
                title = title,
                onBackClicked = { navigator.pop() }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = modifier.padding(paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
        ) {
            posterPagingView(
                items = movies,
                spanCount = 2,
                itemContent = { movie ->
                    HomePosterView(
                        modifier = Modifier
                            .clickable {
                                screenModel.dispatch(
                                    MovieListIntent.OnMovieClicked(movie.id)
                                )
                            }
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                            .heightIn(min = 240.dp),
                        movie = movie
                    )
                }
            )
        }
    }
}
