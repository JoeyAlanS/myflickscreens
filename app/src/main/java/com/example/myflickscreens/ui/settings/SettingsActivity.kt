package com.example.myflickscreens.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myflickscreens.R
import com.example.myflickscreens.ui.login.SignInActivity
import com.example.myflickscreens.ui.settings.NotificationsActivity
import com.example.myflickscreens.ui.settings.EditAccountDataActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        // Configurando a Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Habilitar o botão de voltar na barra superior
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ação para o botão de voltar
        toolbar.setNavigationOnClickListener {
            finish() // Volta para a tela anterior (UserProfileScreen)
        }

        // Configurações de clique para os botões
        findViewById<Button>(R.id.btn_change_account_data).setOnClickListener {
            val intent = Intent(this, EditAccountDataActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_notifications).setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
