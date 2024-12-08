package com.example.myflickscreens.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.example.myflickscreens.databinding.ActivityNotificationsBinding // Importe o Binding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.notificationBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}