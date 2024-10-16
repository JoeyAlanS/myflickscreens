package com.example.myflickscreens.ui.settings

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myflickscreens.R

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
            onBackPressed() // Volta para a tela anterior
        }

        // Configurações de clique para os botões
        findViewById<Button>(R.id.btn_change_account_data).setOnClickListener {
            // Ação para o botão Alterar Dados da Conta
        }

        findViewById<Button>(R.id.btn_notifications).setOnClickListener {
            // Ação para o botão Notificações
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            // Ação para o botão Sair da Conta
        }
    }
}