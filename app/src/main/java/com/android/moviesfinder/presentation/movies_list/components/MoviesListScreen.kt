package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.presentation.movies_list.MoviesListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
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
                movies = state.movies,
                swipeRefreshState = swipeRefreshState,
                viewModel::refresh
            )

            1 -> FavoritesTab()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.error.asString().isNotBlank()) {
                DisplayError(errorText = state.error.asString())
            }

            if (state.isLoading) {
                DisplayLoading()
            }
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

@Composable
fun AllMoviesTab(
    movies: List<Movie>,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit
) {
    val groupedMovies = movies.groupBy {
        getMonthFromReleaseDate(it.releaseDate)
    }
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh }
    ) {
        LazyColumn {
            groupedMovies.forEach { (month, moviesInMonth) ->
                item {
                    Text(
                        text = month,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                items(moviesInMonth) { movie ->
                    MovieListItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun FavoritesTab() {
    // todo Implement favorites logic here
}

@Composable
fun getMonthFromReleaseDate(releaseDate: String): String {
    val monthNumber = releaseDate.split("-")[1].toIntOrNull()
    val months = stringArrayResource(R.array.months_array)
    return months.getOrNull(monthNumber?.minus(1) ?: -1)
        ?: stringResource(R.string.unknown)
}
