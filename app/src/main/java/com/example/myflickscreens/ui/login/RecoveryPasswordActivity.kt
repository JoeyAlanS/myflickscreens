package com.example.myflickscreens.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myflickscreens.R
import com.google.android.material.textfield.TextInputEditText

class RecoveryPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recovery_password)

        val emailRecoveryEt = findViewById<TextInputEditText>(R.id.emailRecoveryEt)
        val recoveryKeyEt = findViewById<TextInputEditText>(R.id.recoveryKeyEt)
        val recoverPasswordButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.recoverPasswordButton)
        val backToLoginText = findViewById<TextView>(R.id.backToLoginText)

        // Ação do botão "Recuperar Senha"
        recoverPasswordButton.setOnClickListener {
            val email = emailRecoveryEt.text.toString()
            val recoveryKey = recoveryKeyEt.text.toString()

            if (email.isNotEmpty() && recoveryKey.isNotEmpty()) {
                // Simulação de recuperação de senha (substituir com lógica real)
                Toast.makeText(this, "Recuperação processada para $email com a chave $recoveryKey", Toast.LENGTH_SHORT).show()
                finish()  // Volta para a tela anterior (login) após a recuperação
            } else {
                Toast.makeText(this, "Por favor, insira seu email e a chave de recuperação", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação do texto "Voltar para o login"
        backToLoginText.setOnClickListener {
            // Voltar para a tela de login
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
