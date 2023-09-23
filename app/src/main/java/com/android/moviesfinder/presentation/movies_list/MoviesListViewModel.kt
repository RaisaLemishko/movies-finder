package com.android.moviesfinder.presentation.movies_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.moviesfinder.R
import com.android.moviesfinder.common.Resource
import com.android.moviesfinder.common.UiText
import com.android.moviesfinder.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel
@Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MoviesListState())
    val state: State<MoviesListState> = _state

    init {
        fetchMovies()
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
                        movies = result.data?.movies ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = MoviesListState(
                        isLoading = false,
                        isRefreshing = false,
                        error = result.message ?: UiText.StringResource(R.string.unexpected_error_occurred)
                    )
                }
                is Resource.Loading -> {
                    _state.value = MoviesListState(isLoading = !isRefreshing, isRefreshing = isRefreshing)
                }
            }
        }.launchIn(viewModelScope)
    }
}
