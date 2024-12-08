package com.example.myflickscreens.ui.login


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RecoveryPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        auth = FirebaseAuth.getInstance()  // Inicializa o FirebaseAuth

        val emailRecoveryEt = findViewById<TextInputEditText>(R.id.emailRecoveryEt)
        val recoverPasswordButton = findViewById<Button>(R.id.recoverPasswordButton)
        val backToLoginText = findViewById<TextView>(R.id.backToLoginText)

        recoverPasswordButton.setOnClickListener {
            val email = emailRecoveryEt.text.toString()

            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email)
            } else {
                Toast.makeText(this, "Por favor, insira seu e-mail", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginText.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "E-mail de recuperação enviado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}
