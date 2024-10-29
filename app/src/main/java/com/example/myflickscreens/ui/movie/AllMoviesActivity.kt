package com.example.myflickscreens.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R

class AllMoviesActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE_TYPE: String = "movie_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movies)

        val featuredRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_featured_movies)
        val allMoviesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_all_movies)

        // Configura o layout manager
        featuredRecyclerView.layoutManager = GridLayoutManager(this, 3) // 3 filmes por linha
        allMoviesRecyclerView.layoutManager = LinearLayoutManager(this) // Lista vertical para todos os filmes

        // Recebe o tipo de filme da Intent
        val movieType = intent.getStringExtra(EXTRA_MOVIE_TYPE)
        val movies = getMoviesByType(movieType) // Método que você deve implementar

        // Separa os filmes em destacados e restantes
        val featuredMovies = movies.take(3) // Pega os primeiros 3 filmes
        val allMovies = movies.drop(3) // Pega o restante

        // Adapta os filmes
        val featuredAdapter = MovieAdapter(featuredMovies)
        val allMoviesAdapter = MovieAdapter(allMovies)

        featuredRecyclerView.adapter = featuredAdapter
        allMoviesRecyclerView.adapter = allMoviesAdapter
    }

    private fun getMoviesByType(movieType: String?): List<Movie> {
        // Aqui você deve implementar a lógica para buscar os filmes do tipo solicitado
        val movieList: List<Movie> = ArrayList()
        // Adicione filmes à lista de acordo com o tipo
        return movieList // Retorne a lista populada
    }
}
