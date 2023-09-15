package com.example.jikan.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.jikan.data.AnimeInfo
import com.example.jikan.viewModels.AnimeSearchViewModel
import com.example.jikan.viewModels.SearchQueryState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(vm: AnimeSearchViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {

    var query by remember { mutableStateOf("") }
    val pagingData = vm.pagingFlow.collectAsLazyPagingItems()
    val list by vm.queriesFlow.collectAsState(initial = emptyList())
    var searchPressed by remember { mutableStateOf(false) }
    SearchBar(query = query,
        onQueryChange = { query = it; if (query == "") searchPressed = false },
        onSearch = {
            if (vm.searchAnime(it)) pagingData.refresh()
            searchPressed = true
        },
        active = true,
        onActiveChange = { }) {
        if (searchPressed && query != "") AnimeSearchResultList(
            pagingData = pagingData,
            onClick = onItemClick
        )
        else QueryHintList(list)
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
fun AnimeSearchResultList(pagingData: LazyPagingItems<AnimeInfo>, onClick: (Int) -> Unit) {
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
            AnimeItem(state = AnimeListElementUiState(el) { onClick(el.id) })
        }
    }
}

