package com.example.myflickscreens.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myflickscreens.api.RetrofitInstance
import com.example.myflickscreens.databinding.FragmentSearchBinding
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.movie.MovieResponse
import com.example.myflickscreens.utils.APIConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieCarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(requireContext())
        movieAdapter = MovieCarouselAdapter(emptyList(), object : MovieCarouselAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                // Ação ao clicar em um filme (adicione sua lógica aqui)
            }
        })
        binding.recyclerViewMovies.adapter = movieAdapter

        // Configurar SearchView
        binding.searchViewMovies.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchMovies(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Você pode implementar uma busca instantânea aqui, se desejado
                return true
            }
        })
    }

    private fun searchMovies(query: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.searchMovies(APIConstants.API_KEY, query)
                }
                movieAdapter.updateMovies(response.results)
            } catch (e: HttpException) {
                // Tratar erro de rede
            } catch (e: Exception) {
                // Tratar outros erros
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
