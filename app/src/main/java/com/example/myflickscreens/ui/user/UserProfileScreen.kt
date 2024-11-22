package com.example.myflickscreens.ui.user

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.settings.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AlertDialog

class UserProfileScreen : Fragment(R.layout.fragment_user_profile), MovieCarouselAdapter.OnItemClickListener {

    private lateinit var favoritesCarousel: RecyclerView
    private lateinit var recentlyWatchedCarousel: RecyclerView
    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileDescription: TextView
    private lateinit var settingsButton: ImageButton
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializando os componentes da UI
        favoritesCarousel = view.findViewById(R.id.favorites_carousel)
        recentlyWatchedCarousel = view.findViewById(R.id.recently_watched_carousel)
        profileImage = view.findViewById(R.id.profile_image) // Ícone de perfil
        profileName = view.findViewById(R.id.profile_name)
        profileDescription = view.findViewById(R.id.profile_description)
        settingsButton = view.findViewById(R.id.settings_button)
        db = FirebaseFirestore.getInstance()

        setupCarousel(favoritesCarousel)
        setupCarousel(recentlyWatchedCarousel)

        // Carregar dados do Firebase
        fetchUserProfile()
        fetchFavorites()
        fetchRecentlyWatched()

        // Configurar o botão de configurações
        settingsButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        // Configurar o clique na descrição para editar
        profileDescription.setOnClickListener {
            showEditDescriptionDialog()  // Exibe a caixa de diálogo para editar a descrição
        }
    }

    private fun setupCarousel(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    // Função para buscar o perfil do usuário
    private fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Buscar dados do usuário no Firestore
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val username = document.getString("username")
                    val description = document.getString("description")
                    // Atualiza o nome e descrição do perfil
                    profileName.text = username ?: "Nome não disponível"
                    profileDescription.text = description ?: "Sem descrição"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro ao carregar perfil: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para atualizar a descrição do usuário no Firestore
    private fun updateDescription(newDescription: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Atualizando a descrição no Firestore
        val userRef = db.collection("users").document(userId)
        userRef.update("description", newDescription)
            .addOnSuccessListener {
                profileDescription.text = newDescription
                Toast.makeText(requireContext(), "Descrição atualizada com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro ao atualizar descrição: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para buscar os filmes favoritos
    private fun fetchFavorites() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(userId).collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val movies = documents.map { doc -> doc.toObject(Movie::class.java) }

                val adapter = favoritesCarousel.adapter
                if (adapter != null && adapter is MovieCarouselAdapter) {
                    adapter.updateMovies(movies)
                } else {
                    favoritesCarousel.adapter = MovieCarouselAdapter(movies, this)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro ao carregar favoritos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para buscar os filmes assistidos recentemente
    private fun fetchRecentlyWatched() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(userId).collection("recentlyWatched")
            .get()
            .addOnSuccessListener { documents ->
                val movies = documents.map { doc -> doc.toObject(Movie::class.java) }

                val adapter = recentlyWatchedCarousel.adapter
                if (adapter != null && adapter is MovieCarouselAdapter) {
                    adapter.updateMovies(movies)
                } else {
                    recentlyWatchedCarousel.adapter = MovieCarouselAdapter(movies, this)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Erro ao carregar filmes assistidos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Função para exibir a caixa de diálogo de edição da descrição
    private fun showEditDescriptionDialog() {
        val userDescription = profileDescription.text.toString()

        // Cria o EditText para editar a descrição
        val editText = EditText(requireContext())
        editText.setText(userDescription)
        editText.setHint("Digite a nova descrição")
        editText.setMaxLines(3)

        // Configura o AlertDialog
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Editar Descrição")
            .setView(editText)
            .setPositiveButton("Salvar") { dialogInterface, _ ->
                val newDescription = editText.text.toString()

                if (newDescription.length <= 100) {
                    updateDescription(newDescription)  // Atualiza a descrição no Firestore
                } else {
                    Toast.makeText(requireContext(), "A descrição não pode ter mais de 100 caracteres", Toast.LENGTH_SHORT).show()
                }
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancelar") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()
    }

    override fun onItemClick(movie: Movie) {
//        val intent = Intent(requireContext(), UserReviewsScreen::class.java)
//        intent.putExtra("movieId", movie.id.toString())  // Converte o Integer para String
//        startActivity(intent)
    }
}
