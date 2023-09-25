package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.datasources.ApiResponse
import com.example.jikan.data.repos.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class TopAnimeViewModel  @Inject constructor(private val repo: AnimeRepository ) : ViewModel() {

    val topAnimeFlow: Flow<TopAnimeItemsState> = flow {
        when (val res = repo.getTopAnime()) {
            is ApiResponse.Success -> emit(TopAnimeItemsState.Success(res.info))
            is ApiResponse.Error -> emit(TopAnimeItemsState.Error(res.error))
        }
    }
}


sealed class TopAnimeItemsState {
    object Loading : TopAnimeItemsState()
    data class Success(val list: List<AnimeInfo>) : TopAnimeItemsState()
    data class Error(val error: Throwable) : TopAnimeItemsState()
}
