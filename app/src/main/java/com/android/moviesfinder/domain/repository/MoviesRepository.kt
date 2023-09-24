package com.android.moviesfinder.domain.repository

import com.android.moviesfinder.data.remote.dto.MoviesResponseDTO


interface MoviesRepository {
    suspend fun getMovies(page: Int): MoviesResponseDTO

}
