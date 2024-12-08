package com.example.myflickscreens.ui.home

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickscreens.R
import com.example.myflickscreens.api.RetrofitInstance
import com.example.myflickscreens.ui.movie.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainNotifications : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_notifications)

        recyclerView = findViewById(R.id.notification_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(emptyList()) { movie ->

        }
        recyclerView.adapter = movieAdapter


        val backButton = findViewById<ImageButton>(R.id.notification_back_button)
        backButton.setOnClickListener {
            finish()
        }


        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        lifecycleScope.launch {
            try {

                val response = RetrofitInstance.api.getNowPlayingMovies()


                withContext(Dispatchers.Main) {
                    movieAdapter = MovieAdapter(response.results) { movie ->

                    }
                    recyclerView.adapter = movieAdapter
                }
            } catch (e: HttpException) {

                withContext(Dispatchers.Main) {
                    movieAdapter = MovieAdapter(emptyList()) { movie -> }
                    recyclerView.adapter = movieAdapter
                }
            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    movieAdapter = MovieAdapter(emptyList()) { movie -> }
                    recyclerView.adapter = movieAdapter
                }
            }
        }
    }
}
