package com.example.data.repository

import com.example.data.datasources.ApiResponse
import com.example.data.datasources.JikanAnimeDataSource
import com.example.model.AnimeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val jikanAnimeDataSource: JikanAnimeDataSource) {

    var topCache: ApiResponse<List<AnimeInfo>>? = null

    suspend fun getAnimeById(id: Int): ApiResponse<AnimeInfo> =
        withContext(Dispatchers.IO) { jikanAnimeDataSource.getAnime(id) }


    suspend fun getTopAnime(): ApiResponse<List<AnimeInfo>> =
        withContext(Dispatchers.IO) {
            if (topCache == null) {
                val res = jikanAnimeDataSource.getTopAnime()
                topCache = res
                res
            } else topCache!!
        }
}
