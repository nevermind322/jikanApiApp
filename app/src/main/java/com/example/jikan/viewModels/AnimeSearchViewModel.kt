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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val searchRepo: SearchQueryRepo,
    private val service: AnimeService
) :
    ViewModel() {

    private var state: SearchState = SearchState("")

    val pagingFlow = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 128)) {
        JikanPagingDataSource(service, state.query, state.params)
    }.flow.cachedIn(viewModelScope)

    val queriesFlow = MutableStateFlow(emptyList<SearchQueryState>())

    init {
        viewModelScope.launch { queriesFlow.emit(getQueries()) }
    }

    fun searchAnimeOnType(newQuery: String): Boolean {
        return (state.query != newQuery).also {
            if (it) state = state.copy(query = newQuery)
        }
    }

    fun searchAnime(newQuery: String): Boolean {
        viewModelScope.launch {
            insertQuery(newQuery)
            queriesFlow.emit(getQueries())
        }
        return searchAnimeOnType(newQuery)
    }

    private suspend fun getQueries(): List<SearchQueryState> = searchRepo.getAll().map {
        SearchQueryState(it, { viewModelScope.launch { searchRepo.deleteQuery(it) } }, {})
    }

    suspend fun insertQuery(query: String) {
        searchRepo.addQuery(query)
    }


}

data class SearchState(
    val query: String, val params: Map<String, String> = mapOf(), val shown: Boolean = false
)

data class SearchQueryState(
    val query: String, val onClick: () -> Unit, val onDeleteClick: () -> Unit
)
