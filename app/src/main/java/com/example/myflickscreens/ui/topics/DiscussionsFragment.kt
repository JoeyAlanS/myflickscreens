package com.example.myflickscreens.ui.topics

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.myflickscreens.R

class DiscussionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de discussões
        return inflater.inflate(R.layout.discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referência ao botão de voltar
        val backButton = view.findViewById<ImageButton>(R.id.discussions_back_button)
        backButton.setOnClickListener {
            // Voltar ao fragmento anterior (HomeFragment)
            parentFragmentManager.popBackStack()
        }

        // Referência ao botão de entrar
        val enterButton = view.findViewById<Button>(R.id.enter_button)
        enterButton.setOnClickListener {
            // Iniciar a atividade de detalhes da discussão
            val intent = Intent(requireContext(), DiscsussionDetail::class.java)
            startActivity(intent)
        }
    }
}
