package com.android.moviesfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAverage: Double,
    val posterUrl: String
)
