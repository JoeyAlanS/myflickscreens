package com.example.myflickscreens.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myflickscreens.R

class MovieAdapter(
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit // Callback para o clique
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        // Configura o clique para chamar o callback passando o filme
        holder.itemView.setOnClickListener { onClick(movie) }
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val imageView: ImageView = itemView.findViewById(R.id.movie_image)

        fun bind(movie: Movie) {
            title.text = movie.title
            val posterUrl = movie.poster_path?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: R.drawable.no_image_placeholder

            Glide.with(itemView.context)
                .load(posterUrl)
                .error(R.drawable.no_image_placeholder)
                .into(imageView)
        }
    }
}

