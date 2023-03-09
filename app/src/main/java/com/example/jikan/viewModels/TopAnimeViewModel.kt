package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import com.example.jikan.ApiResponse
import com.example.jikan.JikanAnimeDataSource
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.repos.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class TopAnimeViewModel() : ViewModel(){

    val repo : AnimeRepository = AnimeRepository(JikanAnimeDataSource())

     val topAnimeFlow : Flow<TopAnimeItemState?> = flow {
        val res = repo.getTopAnime()
        when (res){
            is ApiResponse.Success -> emit(TopAnimeItemState.Success(res.info))
            is ApiResponse.Error -> emit(TopAnimeItemState.Error(res.error))
            else -> {}
        }}
}


sealed class TopAnimeItemState {
    data class Success(val list: List<AnimeInfo>) : TopAnimeItemState()
    data class Error(val error : Throwable) : TopAnimeItemState()
}
