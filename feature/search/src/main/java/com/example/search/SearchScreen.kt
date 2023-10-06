package com.example.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.home.AnimeItem
import com.example.home.AnimeListElementUiState
import com.example.model.AnimeInfo


@Composable
fun SearchScreen(vm: AnimeSearchViewModel = hiltViewModel(), onItemClick: (Int) -> Unit) {

    var query by remember { mutableStateOf("") }
    val pagingData = vm.pagingFlow.collectAsLazyPagingItems()
    val list by vm.queryHintsFlow.collectAsState(initial = emptyList())
    var active by remember { mutableStateOf(false) }

    Column {
        MySearchBar(q = query, onQueryChange = {
            query = it
            if (it == "") active = false
        }, onSearch = {
            active = true
            vm.searchAnime(query)
            pagingData.refresh()
        }, onBackPressed = {})

        if (active) {
            AnimeSearchResultList(pagingData = pagingData, onClick = onItemClick)
        } else {
            QueryHintList(list = list.map {
                SearchQueryUiState(it) {
                    query = it.query
                    active = true
                    vm.searchAnime(query)
                    pagingData.refresh()
                }
            })
        }
    }
}

@Composable
fun MySearchBar(
    q: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        value = q,
        onValueChange = onQueryChange,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable(onClick = onBackPressed)
            )
        },
        placeholder = { Text(text = "EnterName") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(q) }),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

data class SearchQueryUiState(val searchState: SearchQueryState, val onClick: () -> Unit)

@Composable
fun SearchHint(state: SearchQueryUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = state.onClick)
    ) {
        Icon(
            Icons.Default.Clear,
            contentDescription = "delete hint",
            modifier = Modifier.clickable(onClick = state.searchState.onDeleteClick)
        )
        Text(state.searchState.query)
    }
}

@Composable
fun QueryHintList(list: List<SearchQueryUiState>) {
    LazyColumn {
        items(list, key = { it.searchState.query }) { SearchHint(state = it) }
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
        when (it.refresh) {
            is LoadState.Loading -> return
            else -> {}
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        items(pagingData.itemCount, key = pagingData.itemKey { it.id }) {
            val el = pagingData[it]!!
            AnimeItem(state = AnimeListElementUiState(el) {
                onClick(
                    el.id
                )
            })
        }
    }
}

