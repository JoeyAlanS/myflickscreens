package com.example.myflickscreens.ui.home

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myflickscreens.R

class MainNotifications : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_notifications)

        // Configurar o botão de voltar
        val backButton = findViewById<ImageButton>(R.id.notification_back_button)
        backButton.setOnClickListener {
            // Finalizar a atividade atual e voltar para a tela anterior
            finish()
        }

        // Configuração de edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
