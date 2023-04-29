package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jikan.data.datasources.JikanPagingDataSource
import com.example.jikan.data.repos.SearchQueryRepo
import com.example.jikan.db.Entities.SearchQuery

class AnimeSearchViewModel(private val searchQueryRepo : SearchQueryRepo) : ViewModel() {

    private var state: SearchState = SearchState("")



    val pagerFlow = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 128)) {
        JikanPagingDataSource(state.query, state.params)
    }.flow.cachedIn(viewModelScope)

    fun searchAnimeOnType(newQuery: String): Boolean {
        return (state.query != newQuery).also {
            if (it) state = state.copy(query = newQuery)
        }
    }

    fun setShowed(showed: Boolean) {
        state = state.copy(showed = showed)
    }

    fun isShowed() = state.showed
}

data class SearchState(
    val query: String,
    val params: Map<String, String> = mapOf(),
    val showed: Boolean = false
)