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

        movieTitle = findViewById(R.id.movie_title)
        movieRating = findViewById(R.id.movie_rating)
        movieSynopsis = findViewById(R.id.movie_synopsis)
        movieImage = findViewById(R.id.movie_image)
        addButton = findViewById(R.id.add_button)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        fetchMovieDetails(movieId)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener { finish() }

        addButton.setOnClickListener { showRatingPopup() }
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

                movieDetails.poster_path?.let {
                    Glide.with(this@MovieAllDetails)
                        .load("https://image.tmdb.org/t/p/w500$it")
                        .into(movieImage)
                } ?: movieImage.setImageResource(R.drawable.no_image_placeholder)

            } catch (e: HttpException) {
                // Lida com erros de rede
            } catch (e: Exception) {
                // Lida com outros erros
            }
        }
    }

    private fun showRatingPopup() {
        // Seu código para exibir o popup de classificação
    }
}
