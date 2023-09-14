package com.example.jikan.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.compose.JikanTheme
import com.example.jikan.JikanApp
import com.example.jikan.data.AnimeInfo
import com.example.jikan.ui.fragments.DetailScreen
import com.example.jikan.ui.fragments.HomeScreen
import com.example.jikan.ui.fragments.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jikan()
        }
    }
}

@Composable
fun Jikan() {
    val navController = rememberNavController()
    JikanTheme {
        MainNavHost(navController = navController)
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onAnimeClick = { id -> navController.navigate("detail/$id") },
                onSearchClick = { navController.navigate("search") })
        }
        composable("search") { SearchScreen { id -> navController.navigate("detail/$id") } }
        composable("detail/{id}") { DetailScreen(it.arguments!!.getString("id")!!.toInt()) }
    }

}