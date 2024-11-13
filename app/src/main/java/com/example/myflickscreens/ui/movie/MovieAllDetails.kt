package com.example.myflickscreens.ui.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myflickscreens.R
import com.example.myflickscreens.api.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    private val db = FirebaseFirestore.getInstance()

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

        addButton.setOnClickListener { showRatingPopup(movieId) }
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

    private fun showRatingPopup(movieId: Int) {
        // Infla o layout do pop-up
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_rating, null)

        // Cria o AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true) // O diálogo pode ser cancelado tocando fora
            .create()

        // Referências para os botões e RatingBar
        val ratingSlider: RatingBar = dialogView.findViewById(R.id.rating_slider)
        val lastWatchButton: Button = dialogView.findViewById(R.id.last_watch_button)
        val favoriteButton: Button = dialogView.findViewById(R.id.favorite_button)
        val shareButton: Button = dialogView.findViewById(R.id.share_button)

        // Ação para o botão "Últimos assistidos"
        lastWatchButton.setOnClickListener {
            addMovieToRecentlyWatched(movieId)
            showToast("Adicionado aos últimos assistidos")
            dialog.dismiss()
        }

        // Ação para o botão "Adicionar aos favoritos"
        favoriteButton.setOnClickListener {
            addMovieToFavorites(movieId)
            showToast("Adicionado aos favoritos")
            dialog.dismiss()
        }

        // Ação para o botão "Compartilhar"
        shareButton.setOnClickListener {
            shareMovie()
            dialog.dismiss() // Fecha o pop-up
        }

        // Exibe o pop-up
        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addMovieToFavorites(movieId: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val movie = hashMapOf(
            "movieId" to movieId,
            "title" to movieTitle.text.toString(),
            "posterPath" to "https://image.tmdb.org/t/p/w500" // Substitua com o URL real
        )

        db.collection("users").document(userId).collection("favorites")
            .document(movieId.toString())
            .set(movie)
            .addOnSuccessListener {
                showToast("Filme adicionado aos favoritos!")
            }
            .addOnFailureListener {
                showToast("Erro ao adicionar aos favoritos.")
            }
    }

    private fun addMovieToRecentlyWatched(movieId: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val movie = hashMapOf(
            "movieId" to movieId,
            "title" to movieTitle.text.toString(),
            "posterPath" to "https://image.tmdb.org/t/p/w500" // Substitua com o URL real
        )

        db.collection("users").document(userId).collection("recentlyWatched")
            .document(movieId.toString())
            .set(movie)
            .addOnSuccessListener {
                showToast("Filme adicionado aos últimos assistidos!")
            }
            .addOnFailureListener {
                showToast("Erro ao adicionar aos últimos assistidos.")
            }
    }

    private fun shareMovie() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Confira o filme!")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Compartilhar"))
    }
}
