package com.android.moviesfinder.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.android.moviesfinder.BuildConfig
import com.android.moviesfinder.common.BASE_URL
import com.android.moviesfinder.data.data.MoviesRepositoryImpl
import com.android.moviesfinder.data.db.MovieDatabase
import com.android.moviesfinder.data.db.MovieEntity
import com.android.moviesfinder.data.remote.MovieRemoteMediator
import com.android.moviesfinder.data.remote.MoviesApi
import com.android.moviesfinder.domain.repository.MoviesRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
        )
        return ChuckerInterceptor.Builder(context).collector(chuckerCollector).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: ChuckerInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).connectTimeout(30, TimeUnit.SECONDS)

        // Add ChuckerInterceptor only for debug builds
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MoviesApi, @Named("apiKey") apiKey: String): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi = api, apiKey = apiKey)
    }

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.MOVIES_API_KEY

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviePager(
        movieDb: MovieDatabase,
        moviesApi: MoviesApi,
        @Named("apiKey") apiKey: String
    ): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 10,
            ),
            pagingSourceFactory = { movieDb.dao.pagingSource() },
            remoteMediator = MovieRemoteMediator(
                moviesApi = moviesApi,
                movieDb = movieDb,
                apiKey = apiKey
            )
        )
    }
}
