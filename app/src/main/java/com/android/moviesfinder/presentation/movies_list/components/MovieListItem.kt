package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.presentation.movies_list.MoviesListViewModel

@Composable
fun MovieListItem(
    movie: Movie,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(end = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovieImageSection(movie = movie)
            MovieDetailSection(movie = movie, viewModel)
        }
    }
}

@Composable
fun MovieImageSection(movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .size(120.dp)
                .padding(vertical = 8.dp)
        )
        Text(
            text = movie.voteAverage.toString(),
            style = MaterialTheme.typography.labelLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun MovieDetailSection(
    movie: Movie,
    viewModel: MoviesListViewModel
) {
    Column {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = movie.description,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3
        )
        ActionsRow(movie = movie, viewModel = viewModel)
    }
}

@Composable
fun ActionsRow(movie: Movie, viewModel: MoviesListViewModel) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        val moviesState = viewModel.state.value
        val isFavorite = remember(moviesState) {
            moviesState.movies.find { it.id == movie.id }?.isFavorite ?: false
        }

        TextButton(
            onClick = {
                if (isFavorite) {
                    viewModel.removeFavoriteMovie(movie.id)
                } else {
                    viewModel.addFavoriteMovie(movie.id)
                }
            }
        ) {
            if (isFavorite) {
                Text(text = stringResource(id = R.string.unlike))
            } else {
                Text(text = stringResource(id = R.string.like))
            }
        }
    }
}
