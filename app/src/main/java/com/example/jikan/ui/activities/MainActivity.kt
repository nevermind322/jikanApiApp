package com.example.jikan.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.JikanTheme
import com.example.jikan.ui.compose.DetailScreen
import com.example.jikan.ui.compose.HomeScreen
import com.example.jikan.ui.compose.SearchScreen
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