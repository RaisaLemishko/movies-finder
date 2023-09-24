package com.android.moviesfinder.presentation.movies_list

import com.android.moviesfinder.domain.model.Movie

sealed class MovieListItem {
    data class Header(val month: String) : MovieListItem()
    data class MovieItem(val movie: Movie) : MovieListItem()
}
