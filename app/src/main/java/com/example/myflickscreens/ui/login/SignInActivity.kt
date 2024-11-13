package com.example.myflickscreens.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.MainActivity
import com.example.myflickscreens.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        auth = FirebaseAuth.getInstance()  // Inicializa o FirebaseAuth

        val emailEt = findViewById<TextInputEditText>(R.id.emailEt)
        val passwordEt = findViewById<TextInputEditText>(R.id.passET)
        val signInButton = findViewById<Button>(R.id.button)
        val forgotPasswordTextView = findViewById<TextView>(R.id.textView2)

        signInButton.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            // Validação de entrada
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        val signUpTextView = findViewById<TextView>(R.id.textView)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecoveryPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // Impede que o usuário volte para a tela de login
                } else {
                    // Falha no login
                    Toast.makeText(this, "Falha no login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
