package com.example.jikan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikan.data.AnimeInfo
import com.example.jikan.data.datasources.ApiResponse
import com.example.jikan.data.repos.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val repo : AnimeRepository) : ViewModel() {

    suspend fun getAnime(id : Int) = when (val res = repo.getAnimeById(id)) {
        is ApiResponse.Success -> DetailScreenState.Success(info = res.info)
        is ApiResponse.Error -> DetailScreenState.Error(e = res.error)
    }

}

sealed class DetailScreenState() {
    object Loading : DetailScreenState()
    data class Success(val info: AnimeInfo) : DetailScreenState()
    data class Error(val e: Throwable) : DetailScreenState()
}
