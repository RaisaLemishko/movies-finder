package com.android.moviesfinder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.moviesfinder.domain.model.Movie

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

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterUrl,
    isFavorite = isFavorite
)

fun MovieEntity.toFavoriteMovieEntity() = FavoriteMovieEntity(
    id = id,
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterUrl
)
