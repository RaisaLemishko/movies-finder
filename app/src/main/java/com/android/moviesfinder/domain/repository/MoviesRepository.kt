package com.android.moviesfinder.domain.repository

import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.domain.model.Movie

interface MoviesRepository {
    suspend fun getMovies(page: Int): List<Movie>

    suspend fun getMovieById(id: Int): MovieEntity?

}
