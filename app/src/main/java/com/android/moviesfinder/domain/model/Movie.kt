package com.android.moviesfinder.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterUrl: String,
    val isFavorite: Boolean
)
