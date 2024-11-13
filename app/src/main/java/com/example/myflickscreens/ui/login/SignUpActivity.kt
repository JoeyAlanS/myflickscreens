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
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)

        auth = FirebaseAuth.getInstance()  // Inicializa o FirebaseAuth
        db = FirebaseFirestore.getInstance()  // Inicializa o FirebaseFirestore

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
                    createUser(email, password, username)  // Passando o 'username' como parâmetro
                } else {
                    Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Usuário criado com sucesso
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        // Salva o nome do usuário no Firestore
                        val userMap = hashMapOf(
                            "username" to username,
                            "email" to email
                        )
                        db.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener {
                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Falha ao salvar dados do usuário: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Falha no cadastro
                    Toast.makeText(this, "Falha no cadastro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
