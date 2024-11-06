// HomeFragment.kt
package com.example.myflickscreens.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myflickscreens.R
import com.example.myflickscreens.api.RetrofitInstance
import com.example.myflickscreens.databinding.FragmentHomeBinding
import com.example.myflickscreens.ui.movie.AllMoviesActivity
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieAllDetails
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.movie.MovieResponse
import com.example.myflickscreens.ui.topics.DiscussionsFragment
import com.example.myflickscreens.ui.topics.ReviewFragment
import com.example.myflickscreens.utils.APIConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeFragment : Fragment(), MovieCarouselAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCarousels()
        fetchMovies()

        binding.discussions.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DiscussionsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.topReviews.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ReviewFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupCarousels() {
        binding.carouselPopularMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.carouselTopMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.viewAllPopularMovies.setOnClickListener {
            openAllMoviesActivity("popular", "Filmes Populares")
        }

        binding.viewAllTopMovies.setOnClickListener {
            openAllMoviesActivity("top_rated", "Top Filmes")
        }
    }
    private fun openAllMoviesActivity(movieType: String, title: String) {
        val intent = Intent(requireContext(), AllMoviesActivity::class.java).apply {
            putExtra(AllMoviesActivity.EXTRA_MOVIE_TYPE, movieType)
            putExtra(AllMoviesActivity.EXTRA_TITLE, title)
        }
        startActivity(intent)
    }

    private fun fetchMovies() {
        // Inicia as requisições para buscar filmes usando corrotinas
        lifecycleScope.launch {
            try {
                val popularMovies = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getPopularMovies(APIConstants.API_KEY)
                }
                binding.carouselPopularMovies.adapter = MovieCarouselAdapter(popularMovies.results, this@HomeFragment)

                val topRatedMovies = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getTopRatedMovies(APIConstants.API_KEY)
                }
                binding.carouselTopMovies.adapter = MovieCarouselAdapter(topRatedMovies.results, this@HomeFragment)

                // Adicione chamadas para os outros carrosséis da mesma forma
            } catch (e: HttpException) {
                // Trate erros de HTTP
            } catch (e: Exception) {
                // Trate outros erros
            }
        }
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieAllDetails::class.java).apply {
            putExtra("MOVIE_ID", movie.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
