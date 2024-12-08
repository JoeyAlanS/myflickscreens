package com.example.myflickscreens.ui.topics

data class UserReview(
    val reviewId: String = "",
    val userId: String = "",
    val movieId: String = "",
    val title: String = "",
    val rating: Float = 0f,
    val reviewText: String = "",
    val timestamp: Long = 0L,
    val likes: Int = 0,
    val dislikes: Int = 0
)


