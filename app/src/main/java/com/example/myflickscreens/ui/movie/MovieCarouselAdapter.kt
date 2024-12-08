package com.example.myflickscreens.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myflickscreens.R
import com.example.myflickscreens.utils.APIConstants

class MovieCarouselAdapter(private var movieList: List<Movie>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieCarouselAdapter.MovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
        private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title

            val posterUrl = movie.poster_path?.let {
                APIConstants.IMAGE_PATH + it
            }

            Glide.with(itemView.context)
                .load(posterUrl ?: R.drawable.no_image_placeholder)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .into(movieImage)

            itemView.setOnClickListener {
                listener.onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size


    fun updateMovies(newMovies: List<Movie>) {
        movieList = newMovies
        notifyDataSetChanged()
    }
}