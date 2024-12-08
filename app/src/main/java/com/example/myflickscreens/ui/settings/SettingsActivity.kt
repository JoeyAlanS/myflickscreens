package com.example.myflickscreens.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myflickscreens.R
import com.example.myflickscreens.ui.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

class SettingsActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var changeImageButton: Button
    private lateinit var storageReference: StorageReference
    private lateinit var auth: FirebaseAuth
    private val IMAGE_PICK_CODE = 1000
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_screen)

        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        profileImageView = findViewById(R.id.profile_image)
        changeImageButton = findViewById(R.id.btn_change_profile_picture)

        loadProfileImage()

        changeImageButton.setOnClickListener {
            pickImageFromGallery()
        }

        findViewById<Button>(R.id.btn_change_account_data).setOnClickListener {
            val intent = Intent(this, EditAccountDataActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_notifications).setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun loadProfileImage() {
        val profileImageRef = storageReference.child("users/$userId/profile.jpg")
        profileImageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(profileImageView)
        }.addOnFailureListener {
            profileImageView.setImageResource(R.drawable.baseline_profile_24)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            uploadImageToFirebase(imageUri)
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileReference = storageReference.child("users/$userId/profile.jpg")

        fileReference.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    Toast.makeText(this, "Imagem alterada com sucesso!", Toast.LENGTH_SHORT).show()
                    Picasso.get().load(uri).into(profileImageView)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao alterar a imagem.", Toast.LENGTH_SHORT).show()
            }
    }
}
