package com.example.myflickscreens.ui.movie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AllMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movies)

        val titleTextView = findViewById<TextView>(R.id.all_movies_title)
        val allMoviesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_all_movies)
        val backButton = findViewById<ImageView>(R.id.back_button)

        // Define o RecyclerView de todos os filmes com layout de grade de 3 colunas
        allMoviesRecyclerView.layoutManager = GridLayoutManager(this, 3)

        val movieType = intent.getStringExtra(EXTRA_MOVIE_TYPE)
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Filmes"
        titleTextView.text = title

        // Configura o clique no botão de voltar
        backButton.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            val movies = getMoviesByType(movieType)

            // Callback para clique em filme
            val onMovieClick: (Movie) -> Unit = { movie ->
                val intent = Intent(this@AllMoviesActivity, MovieAllDetails::class.java)
                intent.putExtra("MOVIE_ID", movie.id)
                startActivity(intent)
            }

            allMoviesRecyclerView.adapter = MovieAdapter(movies, onMovieClick)
        }
    }

    private suspend fun getMoviesByType(movieType: String?): List<Movie> {
        return try {
            val response = when (movieType) {
                "popular" -> RetrofitInstance.api.getPopularMovies()
                "top_rated" -> RetrofitInstance.api.getTopRatedMovies()
                "now_playing" -> RetrofitInstance.api.getNowPlayingMovies()
                else -> RetrofitInstance.api.getPopularMovies() // Default to popular movies
            }
            response.results
        } catch (e: HttpException) {
            // Lida com o erro HTTP
            emptyList()
        } catch (e: Exception) {
            // Lida com outros erros
            emptyList()
        }
    }

    companion object {
        const val EXTRA_MOVIE_TYPE = "movie_type"
        const val EXTRA_TITLE = "title"
    }
}
