package com.example.myflickscreens.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.example.myflickscreens.databinding.ActivityEditAccountDataBinding

class EditAccountDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aqui você pode adicionar a lógica para salvar os dados alterados
        binding.btnSave.setOnClickListener {
            // Obter os valores dos campos de email, senha e confirmação de senha
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            // Validar os dados (por exemplo, verificar se as senhas coincidem)

            // Salvar os dados (por exemplo, enviar para um servidor ou atualizar um banco de dados local)
        }
    }
}