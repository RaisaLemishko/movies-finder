package com.android.moviesfinder.data.data

import com.android.moviesfinder.data.remote.MoviesApi
import com.android.moviesfinder.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
    private val moviesApi: MoviesApi
) : MoviesRepository {

}
