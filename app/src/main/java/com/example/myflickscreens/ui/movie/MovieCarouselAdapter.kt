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

            // Verifica se o poster_path não é nulo ou vazio antes de tentar carregar a imagem
            val posterUrl = movie.poster_path?.let {
                APIConstants.IMAGE_PATH + it // Combina a URL base com o poster_path
            }

            // Carregar a imagem do poster com Glide, ou uma imagem padrão caso o poster_url seja nulo
            Glide.with(itemView.context)
                .load(posterUrl ?: R.drawable.no_image_placeholder)  // Usa uma imagem padrão caso a URL seja nula
                .placeholder(R.drawable.no_image_placeholder)  // Placeholder enquanto a imagem carrega
                .error(R.drawable.no_image_placeholder)  // Caso o carregamento da imagem falhe
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

    // Função para atualizar a lista de filmes
    fun updateMovies(newMovies: List<Movie>) {
        movieList = newMovies
        notifyDataSetChanged()
    }
}