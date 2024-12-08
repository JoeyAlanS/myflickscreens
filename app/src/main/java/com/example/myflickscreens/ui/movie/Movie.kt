package com.example.myflickscreens.ui.movie

import com.example.myflickscreens.utils.APIConstants

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val poster_path: String? = null
) {

    fun getPosterUrl(): String? {
        return poster_path?.let {
            APIConstants.IMAGE_PATH + it
        }
    }
}

data class MovieResponse(
    val results: List<Movie>
)

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String?,
    val overview: String,
    val cast: List<Actor>
)

data class Actor(
    val name: String,
    val character: String
)