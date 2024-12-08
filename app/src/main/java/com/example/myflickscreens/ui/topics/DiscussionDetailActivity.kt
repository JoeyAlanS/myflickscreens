package com.example.myflickscreens.ui.topics

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class DiscussionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discsussion_detail)

        val discussionId = intent.getStringExtra("discussionId") ?: return
        val participantsContainer = findViewById<LinearLayout>(R.id.participants_container)
        val replyInput = findViewById<EditText>(R.id.reply_input)
        val sendReplyButton = findViewById<Button>(R.id.send_reply_button)

        loadDiscussionDetails(discussionId)
        loadParticipants(discussionId, participantsContainer)
        loadReplies(discussionId)

        sendReplyButton.setOnClickListener {
            val replyText = replyInput.text.toString()
            if (replyText.isNotEmpty()) {
                sendReply(discussionId, replyText)
                replyInput.text.clear()
            } else {
                Toast.makeText(this, "Digite uma resposta!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDiscussionDetails(discussionId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("discussions").document(discussionId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    findViewById<TextView>(R.id.discussion_title).text = document.getString("title")
                    findViewById<TextView>(R.id.discussion_text).text = document.getString("text")
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar detalhes: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun loadParticipants(discussionId: String, container: LinearLayout) {
        val db = FirebaseFirestore.getInstance()
        db.collection("discussions").document(discussionId).get()
            .addOnSuccessListener { document ->
                val participants =
                    document.get("participants") as? List<String> ?: return@addOnSuccessListener
                for (userId in participants) {
                    db.collection("user").document(userId).get()
                        .addOnSuccessListener { userDoc ->
                            val userName = userDoc.getString("username") ?: "Usuário Desconhecido"
                            val userView = TextView(this)
                            userView.text = userName
                            userView.textSize = 16f
                            container.addView(userView)
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Erro ao carregar participantes: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun loadReplies(discussionId: String) {
        val db = FirebaseFirestore.getInstance()
        val repliesContainer = findViewById<LinearLayout>(R.id.participants_container)

        db.collection("discussions").document(discussionId).collection("replies")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { documents ->
                repliesContainer.removeAllViews()
                val tasks = mutableListOf<Task<DocumentSnapshot>>()

                for (document in documents) {
                    val userId = document.getString("userId") ?: "Desconhecido"
                    val text = document.getString("text") ?: ""

                    val task = db.collection("user").document(userId).get()
                    tasks.add(task)

                    task.addOnSuccessListener { userDoc ->
                        val userName = userDoc.getString("username") ?: "Usuário Desconhecido"
                        val replyView = TextView(this)
                        replyView.text = "$userName: $text"
                        replyView.textSize = 16f
                        repliesContainer.addView(replyView)
                    }
                }

                Tasks.whenAll(tasks).addOnSuccessListener {
                    Toast.makeText(this, "Respostas carregadas!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar respostas: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun sendReply(discussionId: String, text: String) {
        val db = FirebaseFirestore.getInstance()
        val userId = "userId"
        val replyData = hashMapOf(
            "userId" to userId,
            "text" to text,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("discussions").document(discussionId).collection("replies").add(replyData)
            .addOnSuccessListener {
                loadReplies(discussionId)
                Toast.makeText(this, "Resposta enviada!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao enviar resposta: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
