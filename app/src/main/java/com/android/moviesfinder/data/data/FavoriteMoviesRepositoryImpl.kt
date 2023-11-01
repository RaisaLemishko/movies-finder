package com.android.moviesfinder.data.data

import androidx.room.withTransaction
import com.android.moviesfinder.data.db.FavoriteMovieEntity
import com.android.moviesfinder.data.db.MovieDatabase
import com.android.moviesfinder.data.db.toFavoriteMovieEntity
import com.android.moviesfinder.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl
@Inject constructor(
    private val movieDb: MovieDatabase
) : FavoriteMoviesRepository {
    override suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> =
        movieDb.dao.getFavoriteMovies()

    override suspend fun getFavoriteMoviesAsFlow(): Flow<List<FavoriteMovieEntity>> =
        movieDb.dao.getFavoriteMoviesAsFlow()

    override suspend fun addFavoriteMovie(id: Int) {
        movieDb.withTransaction {
            val movie = movieDb.dao.getMovieById(id)
            movie?.let {
                val isAlreadyFavorite = movieDb.dao.getFavoriteMovieById(id) != null
                if (!isAlreadyFavorite) {
                    movieDb.dao.addFavoriteMovie(movie.toFavoriteMovieEntity())
                    movieDb.dao.updateMovie(movie.copy(isFavorite = true))
                }
            }
        }
    }


    override suspend fun removeFavoriteMovie(id: Int) {
        movieDb.withTransaction {
            val movie = movieDb.dao.getMovieById(id)
            if (movie != null) {
                movieDb.dao.updateMovie(movie.copy(isFavorite = false))
                movieDb.dao.removeFavoriteMovie(movie.toFavoriteMovieEntity())
            }
        }
    }

    override suspend fun getFavoriteMovieById(id: Int): FavoriteMovieEntity? =
        movieDb.dao.getFavoriteMovieById(id)
}
