package id.tiooooo.nontonterus.pages.home

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.BaseEmptyView
import id.tiooooo.nontonterus.core.ui.component.TitleLeftAndRightView
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.AppLanguage
import id.tiooooo.nontonterus.core.utils.AppTheme
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.core.utils.pushOnce
import id.tiooooo.nontonterus.pages.detail.DetailActivity
import id.tiooooo.nontonterus.pages.home.component.HomePosterView
import id.tiooooo.nontonterus.pages.home.component.HomeRowGenreView
import id.tiooooo.nontonterus.pages.home.component.HomeRowMovieView
import id.tiooooo.nontonterus.pages.home.component.HomeSearchView
import id.tiooooo.nontonterus.pages.home.component.posterPagingView
import id.tiooooo.nontonterus.pages.home.setting.SettingsDialog
import id.tiooooo.nontonterus.pages.list.MovieListRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHomeScreen(
    modifier: Modifier = Modifier,
    screenModel: MovieHomeScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    val state by screenModel.state.collectAsState()
    val topRatedMovies = screenModel.topRatedMovies.collectAsLazyPagingItems()
    val textFieldState = rememberTextFieldState()
    val gridState = rememberLazyGridState()
    val context = LocalContext.current

    val showSearchBar by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0
        }
    }

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is MovieHomeEffect.NavigateToSeeAll -> {
                    navigator.pushOnce(
                        MovieListRoute(
                            type = effect.type,
                            title = effect.title,
                        )
                    )
                }

                is MovieHomeEffect.NavigateToDetailMovie -> {
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, effect.id)
                    }
                    context.startActivity(intent)
                }

                is MovieHomeEffect.NavigateToMovieByGenre -> {
                    navigator.pushOnce(
                        MovieListRoute(
                            genreId = effect.genreId,
                            title = effect.genreName,
                        )
                    )
                }

                is MovieHomeEffect.NavigateToSearch -> {
                    navigator.pushOnce(
                        MovieListRoute(
                            query = effect.query,
                            title = screenModel.localizationManager.getString("home_search_query", effect.query),
                        )
                    )
                }
            }
        }
    }

    BaseScaffold { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = {
                screenModel.dispatch(MovieHomeIntent.FetchInitialData)
                screenModel.dispatch(MovieHomeIntent.UpdateIsRefreshing(true))
                topRatedMovies.refresh()
            },
        ) {
            Column {
                AnimatedVisibility(showSearchBar) {
                    HomeSearchView(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .fillMaxWidth(),
                        state = state,
                        screenModel = screenModel,
                        textFieldState = textFieldState,
                        onSettingsClicked = {
                            screenModel.dispatch(
                                MovieHomeIntent.OnOpenDialogSetting(true)
                            )
                        }
                    )
                }
                LazyVerticalGrid(
                    modifier = modifier.padding(paddingValues),
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        bottom = paddingValues.calculateBottomPadding(),
                        start = MEDIUM_PADDING,
                        end = MEDIUM_PADDING,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
                ) {
                    item(span = { GridItemSpan((2)) }) {
                        HomeRowGenreView(
                            modifier = Modifier,
                            title = stringRes("home_genres"),
                            genres = state.genres,
                            onGenreClicked = { genreId, genreName ->
                                screenModel.dispatch(
                                    MovieHomeIntent.OnGenreClicked(
                                        genreId,
                                        genreName
                                    )
                                )
                            }
                        )
                    }
                    item(span = { GridItemSpan((2)) }) {
                        HomeRowMovieView(
                            modifier = Modifier,
                            title = stringRes("home_now_playing"),
                            movies = state.nowPlayingMovies,
                            onSeeMoreClicked = {
                                screenModel.dispatch(
                                    MovieHomeIntent.OnSeeAllClicked(
                                        type = "now_playing",
                                        title = "home_now_playing",
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
                            title = stringRes("home_popular_movies"),
                            movies = state.popularMovies,
                            onSeeMoreClicked = {
                                screenModel.dispatch(
                                    MovieHomeIntent.OnSeeAllClicked(
                                        type = "popular",
                                        title = "home_popular_movies",
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
                            title = stringRes("home_upcoming_movies"),
                            movies = state.upComingMovies,
                            onSeeMoreClicked = {
                                screenModel.dispatch(
                                    MovieHomeIntent.OnSeeAllClicked(
                                        type = "upcoming",
                                        title = "home_upcoming_movies",
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
                    if (state.isShowTopRated){
                        item(span = { GridItemSpan((2)) }) {
                            TitleLeftAndRightView(
                                modifier = Modifier
                                    .padding(top = SMALL_PADDING)
                                    .fillMaxWidth(),
                                titleLeft = stringRes("home_top_rated"),
                                isSeeMoreEnable = false,
                            )
                        }
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
                        },
                        errorContent = { message, retry ->
                            screenModel.dispatch(MovieHomeIntent.UpdateShowTopRated(false))
                            BaseEmptyView(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(MEDIUM_PADDING),
                                title = stringRes("failed_to_load_data"),
                                animationRes = id.tiooooo.nontonterus.core.R.raw.lottie_popcorn,
                                buttonText = stringRes("try_again"),
                                onButtonClicked = {
                                    retry.invoke()
                                    screenModel.dispatch(MovieHomeIntent.FetchInitialData)
                                },
                            )
                        }
                    )
                }
            }
        }

        if (state.isOpenDialogSetting){
            SettingsDialog(
                onDismiss = {
                    screenModel.dispatch(
                        MovieHomeIntent.OnOpenDialogSetting(false)
                    )
                },
                currentLanguage = AppLanguage.fromValue(state.selectedLanguage),
                currentTheme = AppTheme.fromValue(state.activeTheme),
                onApplied = { newLanguage, newTheme ->
                    screenModel.dispatch(MovieHomeIntent.UpdateLanguage(newLanguage.code))
                    screenModel.dispatch(MovieHomeIntent.UpdateTheme(newTheme.label))
                },
            )
        }
    }
}
