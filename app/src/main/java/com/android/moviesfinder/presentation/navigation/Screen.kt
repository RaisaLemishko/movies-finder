package com.android.moviesfinder.presentation.navigation

sealed class Screen(val route: String) {
    object MoviesListScreen : Screen("movies_list_screen")
}
