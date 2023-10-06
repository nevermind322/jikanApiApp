package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.datasources.JikanPagingDataSource
import com.example.data.repository.SearchQueryRepo
import com.example.network.AnimeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
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

    private val pager = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 48)) {
        JikanPagingDataSource(service, state.query, state.params)
    }

    val pagingFlow = pager.flow.cachedIn(viewModelScope)

    val queryHintsFlow = getQueries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun searchAnime(newQuery: String) {
        viewModelScope.launch { insertQuery(newQuery) }
        if (state.query != newQuery)
            state = state.copy(query = newQuery)
    }


    private fun getQueries(): Flow<List<SearchQueryState>> =
        searchRepo.getAll().map { list ->
            list.map {
                SearchQueryState(it,
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
    val query: String, val onDeleteClick: () -> Unit
)
