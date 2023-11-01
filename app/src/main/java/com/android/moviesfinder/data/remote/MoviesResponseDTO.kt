package com.android.moviesfinder.data.remote


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MoviesResponseDTO(
    val page: Int = 0,
    val results: List<MovieDTO> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)
