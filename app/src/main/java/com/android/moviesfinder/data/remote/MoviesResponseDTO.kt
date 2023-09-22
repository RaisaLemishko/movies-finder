package com.android.moviesfinder.data.remote


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.android.moviesfinder.domain.model.MoviesList

@Keep
data class MoviesResponseDTO(
    val page: Int = 0,
    val results: List<MovieDTO> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)

fun MoviesResponseDTO.toMoviesList() = MoviesList(
    page = page,
    movies = results.map { it.toMovie() },
    totalPages = totalPages,
    totalResults = totalResults
)
