package com.example.myflickscreens.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.example.myflickscreens.databinding.ActivityNotificationsBinding // Importe o Binding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding // Declare o Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater) // Inicialize o Binding
        setContentView(binding.root)

        // Configurar o listener de clique para o botão de voltar
        binding.notificationBackButton.setOnClickListener {
            onBackPressed() // Chamar onBackPressed() para voltar
        }
    }
}