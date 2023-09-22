package com.android.moviesfinder.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("vote_average.gte") voteAverage: Double = DEFAULT_VOTE_AVERAGE,
        @Query("vote_count.gte") voteCount: Int = DEFAULT_VOTE_COUNT,
        @Query("sort_by") sortBy: String = DEFAULT_SORT_BY
    ): MoviesResponseDTO

    companion object {
        // Default values for query parameters
        private const val DEFAULT_VOTE_AVERAGE = 7.0
        private const val DEFAULT_VOTE_COUNT = 100
        private const val DEFAULT_SORT_BY = "primary_release_date.desc"
    }
}
