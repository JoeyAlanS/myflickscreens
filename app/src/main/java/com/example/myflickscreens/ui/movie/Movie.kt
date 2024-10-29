package com.example.myflickscreens.ui.movie

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?
)

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
    val cast: List<Actor> // Supondo que você tenha uma data class para os atores
)

data class Actor(
    val name: String,
    val character: String
)