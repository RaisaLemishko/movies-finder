package com.android.moviesfinder.di

import com.android.moviesfinder.common.BASE_URL
import com.android.moviesfinder.data.data.MoviesRepositoryImpl
import com.android.moviesfinder.data.remote.MoviesApi
import com.android.moviesfinder.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MoviesApi): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi = api)
    }
}
