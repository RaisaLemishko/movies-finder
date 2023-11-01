package com.android.moviesfinder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()

    @Query("SELECT * FROM movies ORDER BY `releaseDate` DESC")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Delete
    suspend fun removeFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies ORDER BY `releaseDate` DESC")
    fun getFavoriteMoviesAsFlow(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getFavoriteMovieById(id: Int): FavoriteMovieEntity?
}
