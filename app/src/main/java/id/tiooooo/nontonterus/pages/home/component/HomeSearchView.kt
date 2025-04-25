package id.tiooooo.nontonterus.pages.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.ZERO_PADDING
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.pages.home.MovieHomeIntent
import id.tiooooo.nontonterus.pages.home.MovieHomeScreenModel
import id.tiooooo.nontonterus.pages.home.MovieHomeState

@Composable
fun HomeSearchView(
    modifier: Modifier = Modifier,
    state: MovieHomeState,
    screenModel: MovieHomeScreenModel,
    textFieldState: TextFieldState,
    onSettingsClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SimpleSearchBar(
            modifier = Modifier.weight(1f),
            expanded = state.isSearchExpand,
            onExpandedChange = {
                screenModel.dispatch(MovieHomeIntent.UpdateSearchExpand(it))
            },
            textFieldState = textFieldState,
            onSearch = { query ->
                screenModel.dispatch(
                    MovieHomeIntent.UpdateMovieFilterParams(
                        state.movieFilterParams.copy(query = query)
                    )
                )
                screenModel.dispatch(MovieHomeIntent.OnSearchQueryClicked(query))
            },
            history = state.searchHistory,
            onAddHistory = { query ->
                if (query.isNotBlank() && state.searchHistory.none {
                        it.query.contains(query, ignoreCase = true)
                    }) {
                    screenModel.dispatch(MovieHomeIntent.SaveSearchQuery(query))
                    screenModel.dispatch(MovieHomeIntent.OnSearchQueryClicked(query))
                }
            },
            onRemoveHistory = { item ->
                screenModel.dispatch(MovieHomeIntent.RemoveSearchQuery(item))
            },
        )

        AnimatedVisibility(!state.isSearchExpand) {
            IconButton(
                modifier = Modifier.padding(end = SMALL_PADDING),
                onClick = { onSettingsClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    history: List<SearchHistoryEntity>,
    onAddHistory: (String) -> Unit,
    onRemoveHistory: (SearchHistoryEntity) -> Unit
) {
    val padding = if (expanded) 0.dp else MEDIUM_PADDING

    Box(
        modifier
            .fillMaxWidth()
            .padding(top = 0.dp)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding, vertical = ZERO_PADDING)
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                    },
                    onSearch = {
                        val query = textFieldState.text.toString()
                        onSearch(query)
                        onAddHistory(query)
                        onExpandedChange.invoke(false)
                    },
                    expanded = expanded,
                    onExpandedChange = { onExpandedChange.invoke(it) },
                    placeholder = {
                        Text(
                            modifier = Modifier.padding(start = SMALL_PADDING),
                            text = stringRes("search"),
                        )
                    },
                    leadingIcon = if (expanded) {
                        {
                            IconButton(
                                onClick = {
                                    onBack?.invoke()
                                    onExpandedChange(false)
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else null,
                    trailingIcon = if (expanded) {
                        {
                            if (textFieldState.text.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        textFieldState.edit { replace(0, length, "") }
                                    }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        }
                    } else null,
                )
            },
            expanded = expanded,
            windowInsets = WindowInsets(top = 0.dp),
            onExpandedChange = { onExpandedChange.invoke(it) },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                if (history.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(MEDIUM_PADDING)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = "No history yet",
                        textAlign = TextAlign.Center,
                    )
                } else {
                    history.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    textFieldState.edit { replace(0, length, item.query) }
                                    onExpandedChange.invoke(false)
                                    onSearch.invoke(item.query)
                                }
                                .padding(horizontal = MEDIUM_PADDING)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = item.query
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = { onRemoveHistory(item) },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Remove")
                            }
                        }
                    }
                }
            }
        }
    }
}
