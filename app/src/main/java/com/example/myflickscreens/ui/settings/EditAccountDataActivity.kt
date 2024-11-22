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

        // Inicializando FirebaseAuth e Firestore
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Obtendo o usuário atual
        currentUser = firebaseAuth.currentUser ?: run {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show()
            finish() // Encerra a atividade se o usuário não estiver autenticado
            return
        }

        // Carregar os dados do usuário atual
        loadUserData()

        // Ao clicar no botão "Salvar", atualizar os dados
        binding.btnSave.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Atualizar o e-mail do usuário
            updateUserData(email, password)
        }
    }

    private fun loadUserData() {
        // Preencher os campos com os dados atuais do usuário
        binding.etEmail.setText(currentUser.email)
    }

    private fun updateUserData(email: String, password: String) {
        // Atualizar o email do usuário
        currentUser.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Se o e-mail foi atualizado com sucesso, também atualiza a senha
                if (password.isNotEmpty()) {
                    currentUser.updatePassword(password).addOnCompleteListener { passwordTask ->
                        if (passwordTask.isSuccessful) {
                            // Atualizar os dados no Firestore
                            val userRef = firestore.collection("users").document(currentUser.uid)
                            userRef.update(
                                "email", email
                            ).addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                                    finish() // Voltar para a tela anterior
                                } else {
                                    Toast.makeText(this, "Erro ao atualizar dados no Firestore.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Erro ao atualizar a senha.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                    finish() // Voltar para a tela anterior
                }
            } else {
                Toast.makeText(this, "Erro ao atualizar e-mail.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
