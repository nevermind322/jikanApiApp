package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.jikan.data.datasources.JikanPagingDataSource

class AnimeSearchViewModel : ViewModel() {

    fun searchAnime(query : String) = Pager(PagingConfig(16, initialLoadSize = 16, maxSize =  128)) {
        JikanPagingDataSource(query)
    }.flow.cachedIn(viewModelScope)
}