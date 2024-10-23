package com.example.myflickscreens.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.databinding.FragmentHomeBinding
import com.example.myflickscreens.ui.movie.Movie
import com.example.myflickscreens.ui.movie.MovieCarouselAdapter
import com.example.myflickscreens.ui.movie.MovieDetails
import com.example.myflickscreens.ui.topics.DiscussionsFragment
import com.example.myflickscreens.ui.topics.ReviewFragment

class HomeFragment : Fragment(), MovieCarouselAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Referências para os TextViews clicáveis
    private lateinit var topReviews: TextView
    private lateinit var discussions: TextView
    private lateinit var chatIcon: ImageView
    private lateinit var  notificationIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar os TextViews
        topReviews = binding.topReviews
        discussions = binding.discussions
        chatIcon = binding.chatIcon
        notificationIcon = binding.notificationIcon

        topReviews.setOnClickListener {
            // Substitua o fragmento atual pelo ReviewFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ReviewFragment())
                .addToBackStack(null) // Permite que o botão de voltar funcione
                .commit()
        }

        discussions.setOnClickListener {
            // Substitua o fragmento atual pelo ReviewFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ReviewFragment())
                .addToBackStack(null) // Permite que o botão de voltar funcione
                .commit()
        }

        chatIcon.setOnClickListener {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }

        notificationIcon.setOnClickListener {
            val intent = Intent(requireContext(), MainNotifications::class.java)
            startActivity(intent)
        }


        // Configuração dos carrosseis
        setupCarousel(binding.carouselLatest)
        setupCarousel(binding.carouselRecommendations)
        setupCarousel(binding.carouselFriendsActivity)
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
        // Navegar para MovieDetailActivity
        val intent = Intent(requireContext(), MovieDetails::class.java).apply {
            putExtra("MOVIE_TITLE", movie.title)
            putExtra("MOVIE_IMAGE_RES_ID", movie.imageResId)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
