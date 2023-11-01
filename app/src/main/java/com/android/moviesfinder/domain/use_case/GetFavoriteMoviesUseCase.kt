package com.android.moviesfinder.domain.use_case

import com.android.moviesfinder.R
import com.android.moviesfinder.common.Resource
import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.data.toMovie
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetFavoriteMoviesUseCase
@Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            favoriteMoviesRepository.getFavoriteMoviesAsFlow()
                .map { entities -> entities.map { it.toMovie() } }
                .collect { movies -> emit(Resource.Success(movies)) }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?.let { UiText.DynamicsString(it) }
                ?: UiText.StringResource(R.string.unexpected_error_occurred)))
        }
    }
}
