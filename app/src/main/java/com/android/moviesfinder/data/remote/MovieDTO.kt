package com.android.moviesfinder.data.remote


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieDTO(
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    val backdropPath: String = "", // /iIvQnZyzgx9TkbrOgcXx0p7aLiq.jpg
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    val id: Int = 0, // 1008042
    @SerializedName("original_language")
    val originalLanguage: String = "", // en
    @SerializedName("original_title")
    val originalTitle: String = "", // Talk to Me
    val overview: String = "", // When a group of friends discover how to conjure spirits using an embalmed hand, they become hooked on the new thrill, until one of them goes too far and unleashes terrifying supernatural forces.
    val popularity: Double = 0.0, // 2292.177
    @SerializedName("poster_path")
    val posterPath: String = "", // /kdPMUMJzyYAc4roD52qavX0nLIC.jpg
    @SerializedName("release_date")
    val releaseDate: String = "", // 2023-07-26
    val title: String = "", // Talk to Me
    val video: Boolean = false, // false
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0, // 7.3
    @SerializedName("vote_count")
    val voteCount: Int = 0 // 686
)
