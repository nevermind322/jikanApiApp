package com.example.jikan.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.compose.JikanTheme
import com.example.jikan.data.AnimeInfo
import com.example.jikan.databinding.FragmentSearchBinding
import com.example.jikan.viewModels.AnimeSearchViewModel
import com.example.jikan.viewModels.SearchQueryState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val animeSearchViewModel: AnimeSearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchResult.setContent {
            JikanTheme {
                SearchScreen(vm = animeSearchViewModel, onClick = ::goToDetail)
            }
        }
        return binding.root
    }

    private fun goToDetail(item: AnimeInfo) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToAnimeDetailFragment(item)
        )
    }
}

@Composable
fun SearchHint(state: SearchQueryState) {
    Row(modifier = Modifier.clickable(onClick = state.onClick)) {
        Icon(
            Icons.Default.Clear,
            contentDescription = "delete hint",
            modifier = Modifier.clickable(onClick = state.onDeleteClick)
        )
        Text(state.query)
    }
}

@Composable
fun QueryHintList(list: List<SearchQueryState>) {
    LazyColumn {
        items(list, key = { it.query }) { SearchHint(state = it) }
    }
}

@Composable
fun AnimeSearchResultList(pagingData: LazyPagingItems<AnimeInfo>, onClick: (AnimeInfo) -> Unit) {
Log.wtf("compose", "animeSearcgList")
    pagingData.loadState.also {
        when (it.prepend) {
            is LoadState.Error -> pagingData.retry()
            else -> {}
        }
        when (it.append) {
            is LoadState.Error -> pagingData.retry()
            else -> {}
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(pagingData.itemCount, key = pagingData.itemKey { it.Title }) {
            val el = pagingData[it]!!
            AnimeItem(state = AnimeListElementUiState(el) { onClick(el) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(vm: AnimeSearchViewModel, onClick: (AnimeInfo) -> Unit) {

    var query by remember { mutableStateOf("") }
    val pagingData = vm.pagingFlow.collectAsLazyPagingItems()
    val list by vm.queriesFlow.collectAsState(initial = emptyList())
    var searchPressed by remember { mutableStateOf(false) }
    SearchBar(query = query, onQueryChange = { query = it; if (query=="") searchPressed = false  }, onSearch = {
        if (vm.searchAnime(it)) pagingData.refresh()
        searchPressed = true
    }, active = true, onActiveChange = { }) {
        if (searchPressed && query != "") AnimeSearchResultList(
            pagingData = pagingData,
            onClick = onClick
        )
        else QueryHintList(list)
    }
}