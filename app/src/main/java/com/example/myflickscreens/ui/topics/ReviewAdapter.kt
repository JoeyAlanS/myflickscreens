package com.example.myflickscreens.ui.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R


class ReviewAdapter(
    private val reviews: List<Pair<UserReview, User>>,
    private val onLikeClick: (String) -> Unit,
    private val onDislikeClick: (String) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userIcon: ImageView = view.findViewById(R.id.user_icon)
        val userName: TextView = view.findViewById(R.id.user_name)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val reviewText: TextView = view.findViewById(R.id.review_text)
        val likeButton: ImageButton = view.findViewById(R.id.like_button)
        val likeCount: TextView = view.findViewById(R.id.like_count)
        val dislikeButton: ImageButton = view.findViewById(R.id.dislike_button)
        val dislikeCount: TextView = view.findViewById(R.id.dislike_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val (review, user) = reviews[position]
        holder.userName.text = user.username
        holder.movieTitle.text = review.title
        holder.reviewText.text = review.reviewText
        holder.likeCount.text = review.likes.toString()
        holder.dislikeCount.text = review.dislikes.toString()

        holder.likeButton.setOnClickListener { onLikeClick(review.reviewId) }
        holder.dislikeButton.setOnClickListener { onDislikeClick(review.reviewId) }
    }

    override fun getItemCount(): Int = reviews.size
}

