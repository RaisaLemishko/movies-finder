package com.android.moviesfinder.presentation.movies_list

import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.domain.model.Movie

data class MoviesListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: UiText = UiText.DynamicsString("")
)
