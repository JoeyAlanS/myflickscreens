package com.example.myflickscreens.ui.movie

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R

class MovieAllDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_all_details)

        // Botão de Voltar para a Home
        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish() // Simula a volta para a tela anterior
        }

        // Botão de Adicionar Nota (+)
        val addButton: Button = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            showRatingPopup()
        }
    }

    private fun showRatingPopup() {
        val dialogView = layoutInflater.inflate(R.layout.popup_rating, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = dialogBuilder.create()

        val slider: RatingBar = dialogView.findViewById(R.id.rating_slider)
        val watchLaterButton: Button = dialogView.findViewById(R.id.watch_later_button)
        val shareButton: Button = dialogView.findViewById(R.id.share_button)

        watchLaterButton.setOnClickListener {
            // Lógica para assistir mais tarde
            dialog.dismiss()
        }

        shareButton.setOnClickListener {
            // Lógica para compartilhar o filme
            dialog.dismiss()
        }

        dialog.show()
    }
}
