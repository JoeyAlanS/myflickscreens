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
    private val movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflando o layout do item_movie para cada item do RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val imageView: ImageView = itemView.findViewById(R.id.movie_image)

        // Função para vincular dados de cada filme aos elementos da UI
        fun bind(movie: Movie) {
            title.text = movie.title

            // Verifica se o poster_path não é nulo, caso contrário, usa imagem padrão
            val posterUrl = movie.poster_path?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: R.drawable.no_image_placeholder

            // Carrega a imagem com Glide, seja do URL ou de um placeholder local
            Glide.with(itemView.context)
                .load(posterUrl)
                .error(R.drawable.no_image_placeholder) // Imagem padrão para erro
                .into(imageView)
        }
    }
}
