package com.example.myflickscreens.ui.movie

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MovieDataBaseUser : AppCompatActivity() {

    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_all_details)

        val addButton: Button = findViewById(R.id.add_button)
        addButton.setOnClickListener { showAddMovieDialog() }
    }

    private fun showAddMovieDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup_rating, null)
        builder.setView(view)

        val lastWatchButton: Button = view.findViewById(R.id.last_watch_button)
        val favoriteButton: Button = view.findViewById(R.id.favorite_button)
        val ratingSlider: RatingBar = view.findViewById(R.id.rating_slider)

        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "Unknown"
        val moviePoster = intent.getStringExtra("MOVIE_POSTER")

        lastWatchButton.setOnClickListener {
            addToFirestore("recentlyWatched", movieTitle, moviePoster)
        }

        favoriteButton.setOnClickListener {
            addToFirestore("favorites", movieTitle, moviePoster)
        }

        // Salvar a nota dada pelo usuário
        ratingSlider.setOnRatingBarChangeListener { _, rating, _ ->
            saveRating(movieTitle, moviePoster, rating)
        }

        builder.create().show()
    }

    private fun addToFirestore(collection: String, title: String, poster: String?) {
        val movie = hashMapOf(
            "title" to title,
            "poster_path" to poster
        )
        db.collection(collection).add(movie)
            .addOnSuccessListener {
                Toast.makeText(this, "Adicionado a $collection com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao adicionar", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveRating(title: String, poster: String?, rating: Float) {
        val movieRating = hashMapOf(
            "title" to title,
            "poster_path" to poster,
            "rating" to rating
        )
        db.collection("ratings").add(movieRating)
            .addOnSuccessListener {
                Toast.makeText(this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar nota", Toast.LENGTH_SHORT).show()
            }
    }
}
