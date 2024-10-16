package com.example.myflickscreens.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar com botão de voltar
        setSupportActionBar(binding.notificationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurar funcionalidades de notificações aqui
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
