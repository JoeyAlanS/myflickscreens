package com.example.myflickscreens.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myflickscreens.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Configurar RecyclerView
//        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(requireContext())
//        // Configure o adaptador quando estiver implementado
//        // binding.recyclerViewMovies.adapter = ...
//
//        // Configurar SearchView
//        binding.searchViewMovies.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Handle search submit
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Handle text changes for search
//                return true
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
