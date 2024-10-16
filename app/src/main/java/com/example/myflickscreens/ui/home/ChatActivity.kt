package com.example.myflickscreens.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar com botão de voltar
        setSupportActionBar(binding.chatToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurar funcionalidades de chat aqui
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
