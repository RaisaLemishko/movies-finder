package com.android.moviesfinder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.android.moviesfinder.presentation.navigation.NavigationHost
import com.android.moviesfinder.presentation.ui.theme.MoviesFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesFinderTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavigationHost()
                }
            }
        }
    }
}
