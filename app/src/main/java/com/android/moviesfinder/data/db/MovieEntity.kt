package com.android.moviesfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int = 1,
    val title: String = "",
    val description: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 1.0,
    val posterUrl: String = "",
    val order: Int = 1,
    val page: Int = 1,
    val isFavorite: Boolean = false
)
