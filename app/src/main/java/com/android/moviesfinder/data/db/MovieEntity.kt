package com.android.moviesfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterUrl: String,
    val order: Int
)
