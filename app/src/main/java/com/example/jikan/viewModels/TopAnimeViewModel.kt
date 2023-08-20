package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import com.example.jikan.data.datasources.ApiResponse
import com.example.jikan.data.datasources.JikanAnimeDataSource
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.repos.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class TopAnimeViewModel  @Inject constructor(private val repo: AnimeRepository ) : ViewModel() {

    val topAnimeFlow: Flow<TopAnimeItemState?> = flow {
        when (val res = repo.getTopAnime()) {
            is ApiResponse.Success -> emit(TopAnimeItemState.Success(res.info))
            is ApiResponse.Error -> emit(TopAnimeItemState.Error(res.error))
        }
    }
}


sealed class TopAnimeItemState {
    data class Success(val list: List<AnimeInfo>) : TopAnimeItemState()
    data class Error(val error: Throwable) : TopAnimeItemState()
}
