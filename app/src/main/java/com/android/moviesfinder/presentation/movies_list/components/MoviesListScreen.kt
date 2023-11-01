package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.moviesfinder.R
import com.android.moviesfinder.presentation.movies_list.MoviesListViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    val tabTitles = listOf(
        stringResource(R.string.tab_all),
        stringResource(R.string.tab_favorites)
    )
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> AllMoviesTab(
                state = state,
                swipeRefreshState = swipeRefreshState,
                viewModel::refresh
            )

            1 -> FavoriteMoviesTab()
        }
    }
}

@Composable
fun DisplayError(errorText: String) {
    Text(
        text = errorText,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun DisplayLoading() {
    CircularProgressIndicator()
}
