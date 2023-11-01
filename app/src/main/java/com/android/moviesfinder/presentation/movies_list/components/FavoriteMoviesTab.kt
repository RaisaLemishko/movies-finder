package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.presentation.movies_list.MoviesListViewModel

@Composable
fun FavoriteMoviesTab(
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val state = viewModel.favoriteMoviesState.value
    val favoriteMovies by viewModel.favoriteMoviesFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            favoriteMovies.isNotEmpty() -> FavoriteMoviesList(favoriteMovies)
            state.error.asString().isNotBlank() -> DisplayError(errorText = state.error.asString())
            state.isLoading -> DisplayLoading()
            favoriteMovies.isEmpty() -> EmptyState()
        }
    }
}

@Composable
private fun FavoriteMoviesList(favoriteMovies: List<Movie>) {
    val groupedMovies = groupMoviesByReleaseMonth(favoriteMovies)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        groupedMovies.forEach { (month, moviesInMonth) ->
            item { MonthHeader(month) }
            items(moviesInMonth) { movie ->
                MovieListItem(movie = movie)
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Text(
        modifier = Modifier.padding(8.dp),
        text = stringResource(id = R.string.no_movies_in_favorites),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}
