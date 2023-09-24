package com.android.moviesfinder.presentation.movies_list.components

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie

@Composable
fun MovieListItem(movie: Movie) {
    Log.d("TAG_RA", "MovieListItem")
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
            MovieDetailSection(movie = movie)
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
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MovieDetailSection(movie: Movie) {
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
        ActionsRow()
    }
}

@Composable
fun ActionsRow() {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.like))
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.unlike))
        }
    }
}
