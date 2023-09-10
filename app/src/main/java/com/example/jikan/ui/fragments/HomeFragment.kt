package com.example.jikan.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.compose.JikanTheme
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentHomeBinding
import com.example.jikan.ui.activities.MainActivity
import com.example.jikan.viewModels.TopAnimeItemState
import com.example.jikan.viewModels.TopAnimeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.topAnimeComposeView.setContent {
            JikanTheme {
                Column {
                    Surface(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(horizontal = 16.dp).clip(RoundedCornerShape(16.dp))
                            .clickable { goToSearchFragment() },
                    ) {
                        Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                            Icon(Icons.Default.Search, contentDescription = "Search bar")
                            Text(text = "Search")
                        }
                    }

                    TopAnimeList(viewModel = viewModel()) {
                        (activity as MainActivity).showAnimePage(
                            it
                        )
                    }
                }
            }
        }
        return binding.root
    }


    private fun goToSearchFragment() {
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(direction)

    }

}

@Composable
fun TopAnimeList(viewModel: TopAnimeViewModel, onItemClick: (AnimeInfo) -> Unit) {

    val uiState by viewModel.topAnimeFlow.collectAsState(initial = TopAnimeItemState.Loading)

    when (uiState) {
        is TopAnimeItemState.Loading -> Text(text = "Loading")
        is TopAnimeItemState.Error -> Text(text = "Error: ${(uiState as TopAnimeItemState.Error).error.message}")
        is TopAnimeItemState.Success -> {
            val data = (uiState as TopAnimeItemState.Success).list
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(data, key = { it.Title }) { AnimeItem(info = it) { onItemClick(it) } }
            }
        }
    }


}

@Composable
fun AnimeItem(info: AnimeInfo, onClick: () -> Unit) {

    Column(Modifier.clickable(onClick = onClick)) {
        NetworkImage(
            url = info.imageUrl!!, modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(text = info.Title, color = Color.White)
    }

}