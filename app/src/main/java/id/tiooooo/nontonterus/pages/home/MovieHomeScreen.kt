package id.tiooooo.nontonterus.pages.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.TitleLeftAndRightView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.pages.detail.MovieDetailRoute
import id.tiooooo.nontonterus.pages.home.component.HomePosterView
import id.tiooooo.nontonterus.pages.home.component.HomeRowMovieView
import id.tiooooo.nontonterus.pages.home.component.posterPagingView
import id.tiooooo.nontonterus.pages.list.MovieListRoute

@Composable
fun MovieHomeScreen(
    modifier: Modifier = Modifier,
    screenModel: MovieHomeScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    val state by screenModel.state.collectAsState()
    val topRatedMovies = screenModel.topRatedMovies.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is MovieHomeEffect.NavigateToSeeAll -> {
                    navigator.push(
                        MovieListRoute(
                            type = effect.type,
                            title = effect.title,
                        )
                    )
                }

                is MovieHomeEffect.NavigateToDetailMovie -> {
                    navigator.push(
                        MovieDetailRoute(movieId = effect.id)
                    )
                }
            }
        }
    }

    BaseScaffold { paddingValues ->
        LazyVerticalGrid(
            modifier = modifier.padding(paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(MEDIUM_PADDING),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
        ) {
            item(span = { GridItemSpan((2)) }) {
                HomeRowMovieView(
                    modifier = Modifier,
                    title = "Now Playing",
                    movies = state.nowPlayingMovies,
                    onSeeMoreClicked = {
                        screenModel.dispatch(
                            MovieHomeIntent.OnSeeAllClicked(
                                type = "now_playing",
                                title = "Now Playing",
                            )
                        )
                    },
                    onMovieClicked = { movie ->
                        screenModel.dispatch(
                            MovieHomeIntent.OnMovieClicked(
                                movie.id
                            )
                        )
                    }
                )
            }
            item(span = { GridItemSpan((2)) }) {
                HomeRowMovieView(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    title = "Popular Movies",
                    movies = state.popularMovies,
                    onSeeMoreClicked = {
                        screenModel.dispatch(
                            MovieHomeIntent.OnSeeAllClicked(
                                type = "popular",
                                title = "Popular Movies",
                            )
                        )
                    },
                    onMovieClicked = { movie ->
                        screenModel.dispatch(
                            MovieHomeIntent.OnMovieClicked(
                                movie.id
                            )
                        )
                    }
                )
            }
            item(span = { GridItemSpan((2)) }) {
                HomeRowMovieView(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    title = "Upcoming Movies",
                    movies = state.upComingMovies,
                    onSeeMoreClicked = {
                        screenModel.dispatch(
                            MovieHomeIntent.OnSeeAllClicked(
                                type = "upcoming",
                                title = "Upcoming Movies",
                            )
                        )
                    },
                    onMovieClicked = { movie ->
                        screenModel.dispatch(
                            MovieHomeIntent.OnMovieClicked(
                                movie.id
                            )
                        )
                    }
                )
            }
            item(span = { GridItemSpan((2)) }) {
                TitleLeftAndRightView(
                    modifier = Modifier
                        .padding(top = SMALL_PADDING)
                        .fillMaxWidth(),
                    titleLeft = "Top Rated",
                    isSeeMoreEnable = false,
                )
            }

            posterPagingView(
                items = topRatedMovies,
                spanCount = 2,
                itemContent = { movie ->
                    HomePosterView(
                        modifier = Modifier
                            .clickable {
                                screenModel.dispatch(
                                    MovieHomeIntent.OnMovieClicked(movie.id)
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




