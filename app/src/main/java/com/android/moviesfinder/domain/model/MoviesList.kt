package com.android.moviesfinder.domain.model


data class MoviesList(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
