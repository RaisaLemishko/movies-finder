package com.android.moviesfinder.domain.use_case

import com.android.moviesfinder.R
import com.android.moviesfinder.common.Resource
import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AddFavoriteMovieUseCase
@Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            favoriteMoviesRepository.addFavoriteMovie(id)
            emit(Resource.Success(true))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?.let { UiText.DynamicsString(it) }
                ?: UiText.StringResource(R.string.unexpected_error_occurred)))
        }
    }
}
