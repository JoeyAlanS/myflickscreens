package com.example.myflickscreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myflickscreens.databinding.ActivityMainBinding
import com.example.myflickscreens.ui.home.HomeFragment
import com.example.myflickscreens.ui.search.SearchFragment
import com.example.myflickscreens.ui.user.UserProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment padrão (HomeFragment)
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_home
            loadFragment(HomeFragment())
        }

        // Configuração da navegação inferior
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(UserProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // Evita recarregar o mesmo fragmento
        if (supportFragmentManager.findFragmentById(binding.fragmentContainer.id)?.javaClass == fragment.javaClass) {
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
