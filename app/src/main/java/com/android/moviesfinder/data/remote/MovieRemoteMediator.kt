package com.android.moviesfinder.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.moviesfinder.data.db.MovieDatabase
import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.data.db.toMovieEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Named

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val moviesApi: MoviesApi,
    private val movieDb: MovieDatabase,
    @Named("apiKey") private val apiKey: String
) : RemoteMediator<Int, MovieEntity>() {
    private var lastRequestedPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("TAG_RA", "REFRESH lastRequestedPage $lastRequestedPage")
                    lastRequestedPage = 1
                    lastRequestedPage
                }

                LoadType.PREPEND -> {
                    Log.d("TAG_RA", "PREPEND lastRequestedPage $lastRequestedPage")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    Log.d("TAG_RA", "APPEND lastRequestedPage $lastRequestedPage")
                    lastRequestedPage++
                    lastRequestedPage
                }
            }
            val movies = moviesApi.getMovies(apiKey = apiKey, page = loadKey)

            delay(3000) // delete
            movieDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDb.dao.clearAll()
                }
                val movieEntities = movies.results.mapIndexed { index, movie ->
                    movie.toMovieEntity().copy(order = (loadKey - 1) * PAGE_SIZE + index)
                }
                movieDb.dao.upsertAll(movieEntities)
            }
            Log.d("TAG_RA", " endOfPaginationReached ${lastRequestedPage >= movies.totalPages}")
            MediatorResult.Success(
                endOfPaginationReached = lastRequestedPage >= movies.totalPages
            )
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("TAG_RA", "Error ${e.message}")
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
