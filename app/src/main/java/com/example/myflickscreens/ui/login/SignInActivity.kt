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

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        val emailEt = findViewById<TextInputEditText>(R.id.emailEt)
        val passwordEt = findViewById<TextInputEditText>(R.id.passET)
        val signInButton = findViewById<Button>(R.id.button)
        val forgotPasswordTextView = findViewById<TextView>(R.id.textView2)

        signInButton.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            // Simulação de login simples (substitua por validação real)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Iniciar a MainActivity após login bem-sucedido
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Impede que o usuário volte para a tela de login
            } else {
                // Mostrar mensagem de erro
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        val signUpTextView = findViewById<TextView>(R.id.textView)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        forgotPasswordTextView.setOnClickListener {
            // Navega para a tela de recuperação de senha
            val intent = Intent(this, RecoveryPasswordActivity::class.java)
            startActivity(intent)
        }
    }

}
