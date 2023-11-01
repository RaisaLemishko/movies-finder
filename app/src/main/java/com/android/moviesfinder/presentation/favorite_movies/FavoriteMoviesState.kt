package com.android.moviesfinder.presentation.favorite_movies

import com.android.moviesfinder.common.UiText

data class FavoriteMoviesState(
    val isLoading: Boolean = false,
    val error: UiText = UiText.DynamicsString("")
)
