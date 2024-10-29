// UserProfileScreen.kt
package com.example.myflickscreens.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.network.ApiService
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieAllDetails
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.settings.SettingsActivity
import com.example.myflickscreens.utils.APIConstants
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserProfileScreen : Fragment(R.layout.fragment_user_profile), MovieCarouselAdapter.OnItemClickListener {

    private lateinit var favoritesCarousel: RecyclerView
    private lateinit var recentlyWatchedCarousel: RecyclerView
    private lateinit var topRatedCarousel: RecyclerView
    private lateinit var popularSeriesCarousel: RecyclerView
    private lateinit var apiService: ApiService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuração do Retrofit
        apiService = Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Configurar carrosseis
        favoritesCarousel = view.findViewById(R.id.favorites_carousel)
        recentlyWatchedCarousel = view.findViewById(R.id.recently_watched_carousel)
        topRatedCarousel = view.findViewById(R.id.favorites_carousel)
        popularSeriesCarousel = view.findViewById(R.id.recently_watched_carousel)

        setupCarousel(favoritesCarousel)
        setupCarousel(recentlyWatchedCarousel)
        setupCarousel(topRatedCarousel)
        setupCarousel(popularSeriesCarousel)

        // Botão de configurações
        val settingsButton: ImageButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        // Obter dados de filmes e séries
        fetchPopularMovies()
        fetchNowPlayingMovies()
        fetchTopRatedMovies()
        fetchPopularSeries()
    }

    private fun setupCarousel(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fetchPopularMovies() {
        lifecycleScope.launch {
            try {
                val response = apiService.getPopularMovies()
                favoritesCarousel.adapter = MovieCarouselAdapter(response.results, this@UserProfileScreen)
            } catch (e: Exception) {
                e.printStackTrace() // Tratamento de erro
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        lifecycleScope.launch {
            try {
                val response = apiService.getNowPlayingMovies()
                recentlyWatchedCarousel.adapter = MovieCarouselAdapter(response.results, this@UserProfileScreen)
            } catch (e: Exception) {
                e.printStackTrace() // Tratamento de erro
            }
        }
    }

    private fun fetchTopRatedMovies() {
        lifecycleScope.launch {
            try {
                val response = apiService.getTopRatedMovies()
                topRatedCarousel.adapter = MovieCarouselAdapter(response.results, this@UserProfileScreen)
            } catch (e: Exception) {
                e.printStackTrace() // Tratamento de erro
            }
        }
    }

    private fun fetchPopularSeries() {
        lifecycleScope.launch {
            try {
                val response = apiService.getPopularSeries()
                popularSeriesCarousel.adapter = MovieCarouselAdapter(response.results, this@UserProfileScreen)
            } catch (e: Exception) {
                e.printStackTrace() // Tratamento de erro
            }
        }
    }

    override fun onItemClick(movie: Movie) {
        // Navegar para os detalhes do filme
        val intent = Intent(requireContext(), MovieAllDetails::class.java).apply {
            putExtra("MOVIE_TITLE", movie.title)
            putExtra("MOVIE_POSTER", movie.poster_path)
        }
        startActivity(intent)
    }
}
