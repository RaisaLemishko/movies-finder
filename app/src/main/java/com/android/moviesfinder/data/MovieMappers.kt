package com.android.moviesfinder.data.db

import com.android.moviesfinder.common.MOVIE_POSTER_BASE_URL
import com.android.moviesfinder.data.remote.dto.MovieDTO
import com.android.moviesfinder.data.remote.dto.MoviesResponseDTO
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.domain.model.MoviesList

fun MovieDTO.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    description = overview,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    posterUrl = MOVIE_POSTER_BASE_URL + posterPath,
    order = 0
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = description,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        posterUrl = posterUrl
    )
}

fun MoviesResponseDTO.toMoviesList() = MoviesList(
    page = page,
    movies = results.map { it.toMovieEntity().toMovie() },
    totalPages = totalPages,
    totalResults = totalResults
)
