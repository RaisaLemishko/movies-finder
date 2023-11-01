package com.android.moviesfinder.presentation.movies_list.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.moviesfinder.R
import com.android.moviesfinder.domain.model.Movie

@Composable
fun MonthHeader(month: String) {
    Text(
        text = month,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun groupMoviesByReleaseMonth(movies: List<Movie>): Map<String, List<Movie>> {
    return movies.groupBy { getMonthFromReleaseDate(it.releaseDate) }
}

@Composable
fun getMonthFromReleaseDate(releaseDate: String): String {
    val monthNumber = releaseDate.split("-")[1].toIntOrNull()
    val months = stringArrayResource(R.array.months_array)
    return months.getOrNull(monthNumber?.minus(1) ?: -1) ?: stringResource(R.string.unknown)
}
