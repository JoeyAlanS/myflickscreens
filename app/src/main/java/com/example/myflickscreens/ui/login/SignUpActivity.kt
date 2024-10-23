package com.example.myflickscreens.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        val emailEt = findViewById<TextInputEditText>(R.id.emailSignUpEt)
        val usernameEt = findViewById<TextInputEditText>(R.id.usernameEt)
        val passwordEt = findViewById<TextInputEditText>(R.id.passwordSignUpEt)
        val confirmPasswordEt = findViewById<TextInputEditText>(R.id.confirmPasswordEt)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val backToLoginTextView = findViewById<TextView>(R.id.backToLoginTextView)

        signUpButton.setOnClickListener {
            val email = emailEt.text.toString()
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()
            val confirmPassword = confirmPasswordEt.text.toString()

            // Validação de campos
            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // Simulação de cadastro (substitua por lógica real)
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    // Navega para a tela de login após o cadastro
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Retornar para a tela de login
        backToLoginTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
