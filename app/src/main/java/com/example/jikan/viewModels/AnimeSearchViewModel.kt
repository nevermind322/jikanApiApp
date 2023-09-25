package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jikan.data.datasources.JikanPagingDataSource
import com.example.jikan.data.repos.SearchQueryRepo
import com.example.jikan.utils.AnimeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val searchRepo: SearchQueryRepo,
    private val service: AnimeService
) : ViewModel() {

    private var state: SearchState = SearchState("")

    val pagingFlow = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 48)) {
        JikanPagingDataSource(service, state.query, state.params)
    }.flow.cachedIn(viewModelScope)

    val queryHintsFlow = getQueries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun searchAnimeOnType(newQuery: String): Boolean {
        return (state.query != newQuery).also {
            if (it) state = state.copy(query = newQuery)
        }
    }

    fun searchAnime(newQuery: String): Boolean {
        viewModelScope.launch { insertQuery(newQuery) }
        return searchAnimeOnType(newQuery)
    }

    private fun getQueries(): Flow<List<SearchQueryState>> =
        searchRepo.getAll().map { list ->
            list.map {
                SearchQueryState(it,
                    onClick = {},
                    onDeleteClick = { viewModelScope.launch { deleteQuery(it) } }
                )
            }
        }

    private fun insertQuery(query: String) {
        viewModelScope.launch { searchRepo.addQuery(query) }

    }

    fun deleteQuery(query: String) {
        viewModelScope.launch { searchRepo.deleteQuery(query) }
    }


}

data class SearchState(
    val query: String, val params: Map<String, String> = mapOf()
)

data class SearchQueryState(
    val query: String, val onClick: () -> Unit, val onDeleteClick: () -> Unit
)
