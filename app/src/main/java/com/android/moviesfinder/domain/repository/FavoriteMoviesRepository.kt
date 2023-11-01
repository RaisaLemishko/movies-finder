package com.android.moviesfinder.domain.repository

import com.android.moviesfinder.data.db.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesRepository {
    suspend fun getFavoriteMoviesAsFlow(): Flow<List<FavoriteMovieEntity>>

    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>

    suspend fun addFavoriteMovie(id: Int)

    suspend fun removeFavoriteMovie(id: Int)

    suspend fun getFavoriteMovieById(id: Int): FavoriteMovieEntity?

}
