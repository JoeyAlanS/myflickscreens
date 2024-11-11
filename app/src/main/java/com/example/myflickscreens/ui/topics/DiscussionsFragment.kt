package com.example.myflickscreens.ui.topics

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myflickscreens.R

class DiscussionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<ImageButton>(R.id.discussions_back_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val enterButton = view.findViewById<Button>(R.id.enter_button)
        enterButton.setOnClickListener {
            val intent = Intent(requireContext(), DiscsussionDetail::class.java)
            startActivity(intent)
        }

        val addDiscussionButton = view.findViewById<View>(R.id.add_discussion_button)
        addDiscussionButton.setOnClickListener {
            showCreateDiscussionDialog()
        }
    }

    private fun showCreateDiscussionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_discussion, null)
        builder.setView(dialogView)

        val titleInput = dialogView.findViewById<EditText>(R.id.new_discussion_title)
        val textInput = dialogView.findViewById<EditText>(R.id.new_discussion_text)

        builder.setPositiveButton("Criar") { _, _ ->
            val title = titleInput.text.toString()
            val text = textInput.text.toString()
            if (title.isNotEmpty() && text.isNotEmpty()) {
                createNewDiscussion(title, text)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun createNewDiscussion(title: String, text: String) {
        // Código para criar e adicionar a nova discussão à lista de discussões
        Toast.makeText(requireContext(), "Discussão '$title' criada!", Toast.LENGTH_SHORT).show()
    }
}
