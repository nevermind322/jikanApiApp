package com.example.detail

import androidx.lifecycle.ViewModel
import com.example.data.datasources.ApiResponse
import com.example.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.model.AnimeInfo

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
