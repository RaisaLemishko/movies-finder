package com.android.moviesfinder.data.data

import com.android.moviesfinder.data.remote.MoviesApi
import com.android.moviesfinder.data.remote.dto.MoviesResponseDTO
import com.android.moviesfinder.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Named

class MoviesRepositoryImpl
@Inject constructor(
    private val moviesApi: MoviesApi,
    @Named("apiKey") private val apiKey: String
) : MoviesRepository {

    override suspend fun getMovies(page: Int): MoviesResponseDTO {
        return moviesApi.getMovies(apiKey = apiKey, page = page)
    }
}
