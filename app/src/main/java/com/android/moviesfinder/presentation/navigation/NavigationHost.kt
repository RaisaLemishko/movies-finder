package com.android.moviesfinder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.moviesfinder.presentation.movies_list.components.MoviesListScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Screen.MoviesListScreen.route
    ) {
        composable(
            route = Screen.MoviesListScreen.route
        ) {
            MoviesListScreen(navController = navController)
        }
    }
}
