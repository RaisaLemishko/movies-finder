package com.android.moviesfinder.data.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.room.withTransaction
import com.android.moviesfinder.data.db.MovieDatabase
import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.data.toMovie
import com.android.moviesfinder.data.remote.MoviesApi
import com.android.moviesfinder.data.toMovieEntity
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl
@Inject constructor(
    private val moviesApi: MoviesApi,
    @Named("apiKey") private val apiKey: String,
    private val movieDb: MovieDatabase,
    private val context: Context
) : MoviesRepository {
    private var lastRequestedPage = 1 // TODO remove hardcoded
    override suspend fun getMovies(page: Int): List<Movie> {
        return if (hasNetworkConnection()) {
            // Fetch from API, store in DB, and return the movies
            try {
                val moviesResponse = moviesApi.getMovies(apiKey = apiKey, page = page)
                movieDb.withTransaction {
                    val favoriteMovies: List<Movie> = movieDb.dao.getFavoriteMovies().map {
                        it.toMovie()
                    }

                    val favoriteMovieIds: List<Int> = favoriteMovies.map { it.id }
                    val movieEntities = moviesResponse.results.mapIndexed { index, movie ->
                        val movieEntity: MovieEntity = movie.toMovieEntity().copy(
                            order = (lastRequestedPage * PAGE_SIZE + index),
                            page = lastRequestedPage,
                            isFavorite = favoriteMovieIds.contains(movie.id)
                        )
                        movieEntity
                    }

                    movieDb.dao.upsertAll(movieEntities)
                }
                movieDb.dao.getMovies().map { it.toMovie() }
            } catch (e: Exception) {
                // In case of any error while fetching from API, return from DB
                movieDb.dao.getMovies().map { it.toMovie() }
            }
        } else {
            // Fetch from DB as there is no network connection
            movieDb.dao.getMovies().map { it.toMovie() }
        }
    }

    override suspend fun getMovieById(id: Int): MovieEntity? = movieDb.dao.getMovieById(id)

    private fun hasNetworkConnection(): Boolean {
        val connectivityManager = //todo replace the deprecated NetworkInfo
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
