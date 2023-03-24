package com.example.jikan.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jikan.data.datasources.JikanPagingDataSource

class AnimeSearchViewModel : ViewModel() {

    private var state: SearchState = SearchState("")

    val pagerFlow = Pager(PagingConfig(16, initialLoadSize = 16, maxSize = 128)) {
        JikanPagingDataSource(state.query, state.params)
    }.flow.cachedIn(viewModelScope)

    fun searchAnimeOnType(newQuery: String): Boolean {
        return if (state.query != newQuery) {
            state = state.copy(query = newQuery)
            true
        } else false
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