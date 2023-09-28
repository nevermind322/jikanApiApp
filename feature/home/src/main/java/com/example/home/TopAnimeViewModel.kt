package com.example.home

import androidx.lifecycle.ViewModel
import com.example.model.AnimeInfo
import com.example.data.datasources.ApiResponse
import com.example.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class TopAnimeViewModel  @Inject constructor(private val repo: AnimeRepository) : ViewModel() {

    val topAnimeFlow: Flow<TopAnimeItemsState> = flow {
        when (val res = repo.getTopAnime()) {
            is ApiResponse.Success -> emit(
                TopAnimeItemsState.Success(
                    res.info
                )
            )
            is ApiResponse.Error -> emit(TopAnimeItemsState.Error(res.error))
        }
    }
}


sealed class TopAnimeItemsState {
    object Loading : TopAnimeItemsState()
    data class Success(val list: List<AnimeInfo>) : TopAnimeItemsState()
    data class Error(val error: Throwable) : TopAnimeItemsState()
}
