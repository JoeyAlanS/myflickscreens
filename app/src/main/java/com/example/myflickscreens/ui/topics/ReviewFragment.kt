package com.example.myflickscreens.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.myflickscreens.R

class ReviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de reviews
        return inflater.inflate(R.layout.top_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referência ao botão de voltar
        val backButton = view.findViewById<ImageButton>(R.id.review_back_button)

        // Ação de clique no botão de voltar
        backButton.setOnClickListener {
            // Voltar ao fragmento anterior (HomeFragment)
            parentFragmentManager.popBackStack()
        }
    }
}
