package com.android.moviesfinder.presentation.movies_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.moviesfinder.R
import com.android.moviesfinder.common.Resource
import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.domain.model.Movie
import com.android.moviesfinder.domain.use_case.AddFavoriteMovieUseCase
import com.android.moviesfinder.domain.use_case.GetFavoriteMoviesUseCase
import com.android.moviesfinder.domain.use_case.GetMoviesUseCase
import com.android.moviesfinder.domain.use_case.RemoveFavoriteMovieUseCase
import com.android.moviesfinder.presentation.favorite_movies.FavoriteMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val favoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MoviesListState())
    val state: State<MoviesListState> = _state
    private val _favoriteMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMoviesFlow: StateFlow<List<Movie>> = _favoriteMoviesFlow.asStateFlow()
    private val _favoriteMoviesState = mutableStateOf(FavoriteMoviesState())
    val favoriteMoviesState: State<FavoriteMoviesState> = _favoriteMoviesState

    init {
        fetchMovies()
        fetchFavoriteMovies()
    }

    fun refresh() {
        fetchMovies(isRefreshing = true)
    }

    private fun fetchMovies(isRefreshing: Boolean = false) {
        getMoviesUseCase(
            page = 1 //todo add real pagination
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MoviesListState(
                        isLoading = !isRefreshing,
                        isRefreshing = isRefreshing,
                        movies = result.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = MoviesListState(
                        isLoading = false,
                        isRefreshing = false,
                        error = result.message
                            ?: UiText.StringResource(R.string.unexpected_error_occurred)
                    )
                }

                is Resource.Loading -> {
                    _state.value =
                        MoviesListState(isLoading = !isRefreshing, isRefreshing = isRefreshing)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchFavoriteMovies() {
        viewModelScope.launch {
            favoriteMoviesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _favoriteMoviesState.value = FavoriteMoviesState(isLoading = false)
                        _favoriteMoviesFlow.value = resource.data ?: emptyList()
                    }

                    is Resource.Error -> {
                        _favoriteMoviesState.value = FavoriteMoviesState(
                            isLoading = false,
                            error = resource.message
                                ?: UiText.StringResource(R.string.unexpected_error_occurred)
                        )
                    }

                    is Resource.Loading -> {
                        _favoriteMoviesState.value = FavoriteMoviesState(isLoading = true)
                    }
                }
            }
        }
    }

    fun addFavoriteMovie(id: Int) {
        addFavoriteMovieUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    toggleFavorite(movieId = id, isFavorite = true)
                }

                is Resource.Error -> {
                    toggleFavorite(movieId = id, isFavorite = false)
                }

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun removeFavoriteMovie(id: Int) {
        removeFavoriteMovieUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    toggleFavorite(movieId = id, isFavorite = false)
                }

                is Resource.Error -> {
                    toggleFavorite(movieId = id, isFavorite = true)

                }

                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        val currentState = _state.value
        val updatedMovies = currentState.movies.map { movie ->
            if (movie.id == movieId) {
                movie.copy(isFavorite = isFavorite)
            } else {
                movie
            }
        }
        _state.value = currentState.copy(movies = updatedMovies)
    }
}
