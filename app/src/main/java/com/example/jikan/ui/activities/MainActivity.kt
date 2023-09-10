package com.example.jikan.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.ActivityMainBinding
import com.example.jikan.ui.fragments.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showAnimePage(info: AnimeInfo) {

        val direction =
            HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(info)

        val navHostFragment = binding.mainNavHost.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        navController.navigate(direction)
    }
}


