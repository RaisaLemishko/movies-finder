package com.android.moviesfinder.data

import com.android.moviesfinder.common.MOVIE_POSTER_BASE_URL
import com.android.moviesfinder.data.db.FavoriteMovieEntity
import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.data.remote.MovieDTO
import com.android.moviesfinder.domain.model.Movie

fun MovieDTO.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    description = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    posterUrl = MOVIE_POSTER_BASE_URL + posterPath,
    order = 0,
    page = 0 // todo: remove
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

fun FavoriteMovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterUrl,
    isFavorite = true
)

fun MovieEntity.toFavoriteMovieEntity() = FavoriteMovieEntity(
    id = id,
    title = title,
    description = description,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterUrl
)
