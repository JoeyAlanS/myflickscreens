package com.example.myflickscreens.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickscreens.MainActivity
import com.example.myflickscreens.R
import com.example.myflickscreens.ui.login.RecoveryPasswordActivity
import com.example.myflickscreens.ui.login.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.gms.common.SignInButton

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        auth = FirebaseAuth.getInstance()  // Inicializa o FirebaseAuth

        val emailEt = findViewById<TextInputEditText>(R.id.emailEt)
        val passwordEt = findViewById<TextInputEditText>(R.id.passET)
        val signInButton = findViewById<Button>(R.id.button)
        val forgotPasswordTextView = findViewById<TextView>(R.id.textView2)

        signInButton.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        val signUpTextView = findViewById<TextView>(R.id.textView)
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecoveryPasswordActivity::class.java)
            startActivity(intent)
        }


        val googleSignInButton = findViewById<SignInButton>(R.id.googleSignInButton)
        googleSignInButton.setOnClickListener {
            googleSignIn()
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Falha no login
                    Toast.makeText(
                        this,
                        "Falha no login: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)  // Método antigo de iniciar o intent
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {

                    Toast.makeText(this, "Falha ao autenticar com Google", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {

                Toast.makeText(this, "Falha ao autenticar com Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Log.e(
                        "GoogleSignIn",
                        "Falha no login com Google",
                        task.exception
                    )
                    Toast.makeText(this, "Falha no login com Google", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
