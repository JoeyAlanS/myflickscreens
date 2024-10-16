package com.example.myflickscreens.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myflickscreens.R


class SearchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_screen)

//        // Setup RecyclerView
//        recyclerViewMovies.layoutManager = LinearLayoutManager(this)
//        // RecyclerView adapter can be set here (when implemented)
//
//        // Handle search actions if necessary (currently just UI, no functionality)
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Handle search submit
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Handle text changes for search as user types
//                return true
//            }
//        })
    }
}
