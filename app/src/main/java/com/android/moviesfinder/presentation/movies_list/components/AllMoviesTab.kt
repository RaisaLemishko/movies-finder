package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.presentation.movies_list.MoviesListState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun AllMoviesTab(
    state: MoviesListState,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.movies.isNotEmpty() -> SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { onRefresh }
            ) {
                MovieList(state.movies)
            }

            state.error.asString().isNotBlank() -> DisplayError(errorText = state.error.asString())
            state.isLoading -> DisplayLoading()
        }
    }
}

@Composable
private fun MovieList(movies: List<Movie>) {
    val groupedMovies = groupMoviesByReleaseMonth(movies)
    LazyColumn {
        groupedMovies.forEach { (month, moviesInMonth) ->
            item { MonthHeader(month) }
            items(moviesInMonth) { movie -> MovieListItem(movie = movie) }
        }
    }
}
