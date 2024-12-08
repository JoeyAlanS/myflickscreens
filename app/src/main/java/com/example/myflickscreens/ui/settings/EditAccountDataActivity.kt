package com.example.myflickscreens.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.example.myflickscreens.databinding.ActivityEditAccountDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser

class EditAccountDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountDataBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        currentUser = firebaseAuth.currentUser ?: run {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadUserData()

        binding.btnSave.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserData(email, password)
        }
    }

    private fun loadUserData() {
        binding.etEmail.setText(currentUser.email)
    }

    private fun updateUserData(email: String, password: String) {
        currentUser.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (password.isNotEmpty()) {
                    currentUser.updatePassword(password).addOnCompleteListener { passwordTask ->
                        if (passwordTask.isSuccessful) {
                            val userRef = firestore.collection("users").document(currentUser.uid)
                            userRef.update(
                                "email", email
                            ).addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Dados atualizados com sucesso.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Erro ao atualizar dados no Firestore.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Erro ao atualizar a senha.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Erro ao atualizar e-mail.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
