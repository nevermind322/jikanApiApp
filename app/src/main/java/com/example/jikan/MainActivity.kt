package com.example.jikan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.design_system.theme.JikanTheme

import com.example.detail.DetailScreen
import com.example.home.HomeScreen
import com.example.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
        composable("detail/{id}") {
            DetailScreen(
                it.arguments!!.getString("id")!!.toInt()
            )
        }
    }

}