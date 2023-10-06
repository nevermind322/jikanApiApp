package com.example.data.datasources

import com.example.data.mapToModel
import com.example.model.AnimeInfo
import com.example.network.AnimeJson
import com.example.network.AnimeService
import javax.inject.Inject

class JikanAnimeDataSource @Inject constructor(private val animeService: AnimeService) {
    suspend fun getAnime(id: Int): ApiResponse<AnimeInfo> {
        val serverResponse = animeService.getAnimeById(id)
        return if (serverResponse.isSuccessful) {
            val infoJson = serverResponse.body()
            if (infoJson == null) ApiResponse.Error(Exception("deserialization error"))
            else {
                val info = mapToModel(infoJson.data)
                ApiResponse.Success(info)
            }
        } else ApiResponse.Error(Exception("Unsuccessful call: ${serverResponse.code()}"))
    }

    suspend fun getTopAnime(): ApiResponse<List<AnimeInfo>> {
        val serverResponse = animeService.getTopAnime(mapOf())
        if (serverResponse.isSuccessful) {
            val infoJson = serverResponse.body()
            infoJson ?: return ApiResponse.Error(Exception("deserialization error"))
            val infoList = infoJson.data.map(::mapToModel)
            return ApiResponse.Success(infoList)
        }
        return ApiResponse.Error(Exception("Unsuccessful call: ${serverResponse.code()}"))
    }
}


sealed class ApiResponse<T> {
    data class Success<T>(val info: T) : ApiResponse<T>()
    data class Error<T>(val error: Throwable) : ApiResponse<T>()
}

