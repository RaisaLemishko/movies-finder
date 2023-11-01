package com.android.moviesfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.moviesfinder.domain.model.Movie

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

fun FavoriteMovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterUrl,
    isFavorite = true
)
