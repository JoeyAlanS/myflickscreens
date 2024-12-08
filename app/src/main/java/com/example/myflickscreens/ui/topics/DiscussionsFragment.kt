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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DiscussionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addDiscussionButton = view.findViewById<View>(R.id.add_discussion_button)
        val discussionsContainer = view.findViewById<LinearLayout>(R.id.discussions_container)

        addDiscussionButton.setOnClickListener {
            showCreateDiscussionDialog(discussionsContainer)
        }

        loadDiscussions(discussionsContainer)
    }

    private fun showCreateDiscussionDialog(discussionsContainer: LinearLayout) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_discussion, null)
        builder.setView(dialogView)

        val titleInput = dialogView.findViewById<EditText>(R.id.new_discussion_title)
        val textInput = dialogView.findViewById<EditText>(R.id.new_discussion_text)

        builder.setPositiveButton("Criar") { _, _ ->
            val title = titleInput.text.toString()
            val text = textInput.text.toString()
            if (title.isNotEmpty() && text.isNotEmpty()) {
                createDiscussion(title, text, discussionsContainer)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun createDiscussion(title: String, text: String, discussionsContainer: LinearLayout) {
        val db = FirebaseFirestore.getInstance()
        val userId = "userId123"
        val discussionId = db.collection("discussions").document().id

        val discussionData = hashMapOf(
            "creatorId" to userId,
            "title" to title,
            "text" to text,
            "timestamp" to FieldValue.serverTimestamp(),
            "participants" to arrayListOf(userId)
        )

        db.collection("discussions").document(discussionId).set(discussionData)
            .addOnSuccessListener {
                loadDiscussionItem(title, discussionId, discussionsContainer)
                Toast.makeText(requireContext(), "Discussão criada!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadDiscussions(discussionsContainer: LinearLayout) {
        val db = FirebaseFirestore.getInstance()
        db.collection("discussions").get()
            .addOnSuccessListener { documents ->
                discussionsContainer.removeAllViews()
                if (documents.isEmpty) {
                    Toast.makeText(
                        requireContext(),
                        "Nenhum tópico encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    for (document in documents) {
                        val title = document.getString("title") ?: "Sem Título"
                        val discussionId = document.id
                        loadDiscussionItem(title, discussionId, discussionsContainer)
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Erro ao carregar discussões: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun loadDiscussionItem(title: String, discussionId: String, container: LinearLayout) {
        val discussionView = layoutInflater.inflate(R.layout.item_discussion, null)
        val titleTextView = discussionView.findViewById<TextView>(R.id.discussion_title)
        val enterButton = discussionView.findViewById<Button>(R.id.enter_button)

        titleTextView.text = title
        enterButton.setOnClickListener {
            val intent = Intent(requireContext(), DiscussionDetailActivity::class.java)
            intent.putExtra("discussionId", discussionId)
            startActivity(intent)
        }

        container.addView(discussionView)
    }

    fun onBackPressed(view: View) {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}
