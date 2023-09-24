package com.android.moviesfinder.presentation.movies_list.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.presentation.movies_list.MoviesListViewModel

@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val lazyMovies = viewModel.moviePagingFlow.collectAsLazyPagingItems()

    val tabTitles = listOf(
        stringResource(R.string.tab_all),
        stringResource(R.string.tab_favorites)
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

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
                lazyMovies = lazyMovies,
            )

            1 -> FavoritesTab()
        }
    }
}


@Composable
fun DisplayError(errorText: String) {
    Log.d("TAG_RA", "DisplayError")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun DisplayLoading() {
    Log.d("TAG_RA", "DisplayLoading")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun AllMoviesTab(
    lazyMovies: LazyPagingItems<Movie>,
) {
    LazyColumn {
        items(
            count = lazyMovies.itemCount,
            key = lazyMovies.itemKey { movie -> movie.id }
        ) { index ->
            val item = lazyMovies[index]
            if (item != null) {
                MovieListItem(movie = item)
            }
        }

        lazyMovies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { DisplayLoading() }
                }

                loadState.append is LoadState.Loading -> {
                    item { DisplayLoading() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = lazyMovies.loadState.refresh as? LoadState.Error
                    item {
                        DisplayError(
                            e?.error?.localizedMessage ?: stringResource(
                                id = R.string.unexpected_error_occurred
                            )
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val e = lazyMovies.loadState.append as? LoadState.Error
                    item {
                        DisplayError(
                            e?.error?.localizedMessage ?: stringResource(
                                id = R.string.unexpected_error_occurred
                            )
                        )
                    }
                }
            }
        }
    }
    /*
        val loadedMovies = lazyMovies.itemSnapshotList.items

        val movieListItems = mutableListOf<MovieListItem>()
        loadedMovies.forEach { movie ->
            val month = getMonthFromReleaseDate(movie.releaseDate)
            if (movieListItems.isEmpty() || (movieListItems.last() is MovieListItem.MovieItem && getMonthFromReleaseDate((movieListItems.last() as MovieListItem.MovieItem).movie.releaseDate) != month)) {
                movieListItems.add(MovieListItem.Header(month))
            }
            movieListItems.add(MovieListItem.MovieItem(movie))
        }

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { onRefresh }
        ) {
            LazyColumn {
                itemsIndexed(movieListItems) { index, item ->
                    when (item) {
                        is MovieListItem.Header -> MonthHeader(month = item.month)
                        is MovieListItem.MovieItem -> MovieListItem(movie = item.movie)
                    }
                }

                if (lazyMovies.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }*/
}


fun getMonthFromReleaseDate(releaseDate: String): String {
    val monthNumber = releaseDate.split("-")[1].toIntOrNull()
    val year = releaseDate.split("-")[0].toIntOrNull()
    val months = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    return "${months.getOrNull(monthNumber?.minus(1) ?: -1)} $year" ?: "Unknown"
}


@Composable
fun MonthHeader(month: String) {
    Log.d("TAG_RA", "MonthHeader")
    Text(
        text = month,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun FavoritesTab() {
    // todo Implement favorites logic here
}
