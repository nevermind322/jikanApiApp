package com.example.jikan.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.jikan.*
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.repos.AnimeRepository
import com.example.jikan.databinding.ActivityMainBinding
import com.example.jikan.ui.fragments.TopAnimeWithSearchFragmentDirections
import com.example.jikan.viewModels.AnimeInfoUiState
import com.example.jikan.viewModels.AnimeViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val animeViewModel by viewModels<AnimeViewModel> {
        AnimeViewModel.AnimeViewModelProviderFactory(AnimeRepository((JikanAnimeDataSource())))
    }

    private lateinit var binding : ActivityMainBinding

    init {
        Log.i("lifecycle", "$this created")
    }

    protected fun finalize(){
        Log.i("lifecycle", "$this dead")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animeViewModel.resetValue()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeViewModel.uiState.collect {
                    Log.i("nav", "collected $it")
                    when (it) {
                        is AnimeInfoUiState.Error -> showError(it.e)
                        is AnimeInfoUiState.Success ->  showAnimePage(it.info)
                        else -> {}
                    }
                }
            }
        }
    }

     fun showAnimePage(info: AnimeInfo) {

        val direction = TopAnimeWithSearchFragmentDirections.actionTopAnimeWithSearchFragmentToAnimeDetailFragment(info)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        Log.i("nav", "i im navigating from ${navController.currentDestination} to $direction")
        navController.navigate(direction)
    }

    private fun showError(error: Throwable) {
        makeToast(error.message)
    }

    private fun makeToast(message: String?) {
        val toast = Toast.makeText(this, message ?: "Unknown error", Toast.LENGTH_LONG)
        toast.show()
    }
}


