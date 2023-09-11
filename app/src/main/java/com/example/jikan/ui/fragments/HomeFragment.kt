package com.example.jikan.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.compose.JikanTheme
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentHomeBinding
import com.example.jikan.ui.activities.MainActivity
import com.example.jikan.viewModels.TopAnimeItemsState
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
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
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
fun TopAnimeList(viewModel: TopAnimeViewModel, onClick: (AnimeInfo) -> Unit) {

    val uiState by viewModel.topAnimeFlow.collectAsState(initial = TopAnimeItemsState.Loading)

    when (uiState) {
        is TopAnimeItemsState.Loading -> Text(text = "Loading")
        is TopAnimeItemsState.Error -> Text(text = "Error: ${(uiState as TopAnimeItemsState.Error).error.message}")
        is TopAnimeItemsState.Success -> {
            val data = (uiState as TopAnimeItemsState.Success).list
            AnimeItemList(data.map { AnimeListElementUiState(it) { onClick(it) } })
        }
    }


}

data class AnimeListElementUiState(val info: AnimeInfo, val onClick: () -> Unit)

@Composable
private fun AnimeItemList(
    data: List<AnimeListElementUiState>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(data, key = { it.info.Title }) { AnimeItem(it) }
    }
}

@Composable
fun AnimeItem(state: AnimeListElementUiState) {

    Column(Modifier.clickable(onClick = state.onClick)) {
        NetworkImage(
            url = state.info.imageUrl!!, modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(text = state.info.Title, color = Color.White)
    }

}