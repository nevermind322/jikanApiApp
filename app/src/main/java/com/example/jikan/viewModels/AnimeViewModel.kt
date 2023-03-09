package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jikan.ApiResponse
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.repos.AnimeRepository
import com.example.jikan.utils.PagedAnimeResponse
import kotlinx.coroutines.flow.MutableStateFlow

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel() {

    class AnimeViewModelProviderFactory(private val repository: AnimeRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnimeViewModel::class.java))
                return AnimeViewModel(repository) as T
            throw IllegalArgumentException()
        }
    }

    val uiState : MutableStateFlow<AnimeInfoUiState?> = MutableStateFlow(null)

    suspend  fun findAnimeById(id : Int){
        when(val res = repository.getAnimeById(id)){
            is ApiResponse.Error -> uiState.value = AnimeInfoUiState.Error(res.error)
            is ApiResponse.Success -> uiState.value = AnimeInfoUiState.Success(res.info)
            else -> {}
        }
    }

    fun resetValue() {
        uiState.value = null
    }
}

sealed class  AnimeInfoUiState{
    data class Success(val info: AnimeInfo) : AnimeInfoUiState()
    data class Error(val e: Throwable) : AnimeInfoUiState()
}