package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jikan.data.datasources.JikanPagingDataSource
import com.example.jikan.data.repos.SearchQueryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(private val searchRepo: SearchQueryRepo) :
    ViewModel() {

    private var state: SearchState = SearchState("")


    val pagerFlow = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 128)) {
        JikanPagingDataSource(state.query, state.params)
    }.flow.cachedIn(viewModelScope)

    fun searchAnimeOnType(newQuery: String): Boolean {
        return (state.query != newQuery).also {
            if (it) state = state.copy(query = newQuery)
        }
    }

    suspend fun getQueries(): List<SearchQueryState> =
        searchRepo.getAll().map {
            SearchQueryState(it,
                { viewModelScope.launch { searchRepo.deleteQuery(it) } },
                {})
        }

    suspend fun insertQuery(query: String) {
        searchRepo.addQuery(query)
    }
    fun setShowed(showed: Boolean) {
        state = state.copy(shown = showed)
    }

    fun isShowed() = state.shown

}

data class SearchState(
    val query: String,
    val params: Map<String, String> = mapOf(),
    val shown: Boolean = false
)

class SearchQueryState(
    val query: String,
    val onClick: () -> Unit,
    val onLongClick: () -> Unit
)
