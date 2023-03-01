package com.example.jikan

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.distinctUntilChanged

class MainActivity : AppCompatActivity() {
    private val animeViewModel by viewModels<AnimeViewModel> {
        AnimeViewModel.AnimeViewModelProviderFactory(AnimeRepository((JikanAnimeDataSource())))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    private fun showAnimePage(info: AnimeInfo) {

        val direction = SearchFragmentDirections.actionSearchFragmentToAnimeDetailFragment(info)
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


