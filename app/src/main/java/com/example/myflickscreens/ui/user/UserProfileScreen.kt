package com.example.myflickscreens.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.settings.SettingsActivity

class UserProfileScreen : Fragment(R.layout.fragment_user_profile), MovieCarouselAdapter.OnItemClickListener {

    private lateinit var favoritesCarousel: RecyclerView
    private lateinit var recentlyWatchedCarousel: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar os carrosseis
        favoritesCarousel = view.findViewById(R.id.favorites_carousel)
        setupCarousel(favoritesCarousel)

        recentlyWatchedCarousel = view.findViewById(R.id.recently_watched_carousel)
        setupCarousel(recentlyWatchedCarousel)

        // Botão de configurações
        val settingsButton: ImageButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCarousel(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MovieCarouselAdapter(getSampleMovies(), this)
    }

    private fun getSampleMovies(): List<Movie> {
        return listOf(
            Movie("Filme 1", R.drawable.no_image_placeholder),
            Movie("Filme 2", R.drawable.no_image_placeholder),
            Movie("Filme 3", R.drawable.no_image_placeholder),
            Movie("Filme 4", R.drawable.no_image_placeholder),
            Movie("Filme 5", R.drawable.no_image_placeholder)
        )
    }

    override fun onItemClick(movie: Movie) {
        // Navegar para detalhes do filme
    }
}
