package com.example.myflickscreens.ui.movie

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myflickscreens.R
import com.example.myflickscreens.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MovieAllDetails : AppCompatActivity() {

    private lateinit var movieTitle: TextView
    private lateinit var movieRating: TextView
    private lateinit var movieSynopsis: TextView
    private lateinit var movieImage: ImageView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_all_details)

        // Inicializa os componentes da UI
        movieTitle = findViewById(R.id.movie_title)
        movieRating = findViewById(R.id.movie_rating)
        movieSynopsis = findViewById(R.id.movie_synopsis)
        movieImage = findViewById(R.id.movie_image)
        addButton = findViewById(R.id.add_button)

        // Pega o ID do filme a partir do Intent
        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        fetchMovieDetails(movieId)

        // Botão de Voltar para a Home
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Simula a volta para a tela anterior
        }

        // Botão de Adicionar Nota (+)
        addButton.setOnClickListener {
            showRatingPopup()
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        lifecycleScope.launch {
            try {
                val movieDetails = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getMovieDetails(movieId)
                }

                movieTitle.text = movieDetails.title
                movieRating.text = "Nota: ${movieDetails.vote_average}"
                movieSynopsis.text = movieDetails.overview

                // Carregue a imagem usando Glide com o caminho correto do poster
                movieDetails.poster_path?.let {
                    Glide.with(this@MovieAllDetails)
                        .load("https://image.tmdb.org/t/p/w500$it")
                        .into(movieImage)
                } ?: run {
                    // Aqui você pode definir uma imagem padrão se o poster não estiver disponível
                    movieImage.setImageResource(R.drawable.no_image_placeholder) // Substitua pelo seu recurso padrão
                }

            } catch (e: HttpException) {
                // Trate erros de HTTP
            } catch (e: Exception) {
                // Trate outros erros
            }
        }
    }

    private fun showRatingPopup() {
        // (Seu código existente para o popup)
    }
}
