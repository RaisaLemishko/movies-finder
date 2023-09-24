package com.android.moviesfinder.domain.use_case

import com.android.moviesfinder.R
import com.android.moviesfinder.common.Resource
import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.data.db.toMoviesList
import com.android.moviesfinder.domain.model.MoviesList
import com.android.moviesfinder.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetMoviesUseCase
@Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(page: Int): Flow<Resource<MoviesList>> = flow {
        try {
            emit(Resource.Loading())
            val movies = moviesRepository.getMovies(page).toMoviesList()
            emit(Resource.Success(movies))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.no_connection_error)))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    e.localizedMessage?.let { UiText.DynamicsString(it) }
                        ?: UiText.StringResource(R.string.unexpected_error_occurred)
                )
            )
        }
    }
}
