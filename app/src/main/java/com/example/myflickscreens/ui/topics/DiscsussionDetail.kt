package com.example.myflickscreens.ui.topics

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.R

class DiscsussionDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discsussion_detail)

        val replyButton = findViewById<TextView>(R.id.reply_text)
        val replyInputLayout = findViewById<LinearLayout>(R.id.reply_input_layout)
        val sendButton = findViewById<Button>(R.id.send_reply_button)

        replyButton.setOnClickListener {
            replyInputLayout.visibility = View.VISIBLE
        }

        sendButton.setOnClickListener {
            val replyInput = findViewById<EditText>(R.id.reply_input)
            val response = replyInput.text.toString()
            if (response.isNotEmpty()) {
                addReply(response, "Nome Principal", findViewById(R.id.replies_layout), 0)
                replyInput.text.clear()
                replyInputLayout.visibility = View.GONE
            }
        }
    }

    // Função para adicionar uma nova resposta
    private fun addReply(responseText: String, userName: String, parentLayout: LinearLayout, level: Int) {
        val replyView = layoutInflater.inflate(R.layout.item_reply, null)
        val replyText = replyView.findViewById<TextView>(R.id.reply_text_view)
        val userText = replyView.findViewById<TextView>(R.id.user_name)
        val replyContainer = replyView.findViewById<LinearLayout>(R.id.nested_replies_container)
        val nestedReplyButton = replyView.findViewById<TextView>(R.id.nested_reply_button)

        replyText.text = responseText
        userText.text = userName

        // Configura a linha de hierarquia com base no nível de resposta
        val hierarchyLine = replyView.findViewById<View>(R.id.hierarchy_line)
        if (level > 0) {
            hierarchyLine.visibility = View.VISIBLE
        }

        nestedReplyButton.setOnClickListener {
            showNestedReplyInput(replyContainer, level + 1)
        }

        parentLayout.addView(replyView)
    }

    // Função para mostrar o campo de resposta aninhada
    private fun showNestedReplyInput(parentLayout: LinearLayout, level: Int) {
        val inputLayout = layoutInflater.inflate(R.layout.nested_reply_input, null)
        val nestedInput = inputLayout.findViewById<EditText>(R.id.nested_reply_input)
        val nestedSendButton = inputLayout.findViewById<Button>(R.id.nested_send_reply_button)

        nestedSendButton.setOnClickListener {
            val response = nestedInput.text.toString()
            if (response.isNotEmpty()) {
                addReply(response, "Nome do Usuário", parentLayout, level)
                parentLayout.removeView(inputLayout)
            }
        }

        parentLayout.addView(inputLayout)
    }
}
