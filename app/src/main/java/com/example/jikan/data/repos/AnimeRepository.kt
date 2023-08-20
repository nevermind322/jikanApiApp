package com.example.jikan.data.repos

import com.example.jikan.data.datasources.ApiResponse
import com.example.jikan.data.datasources.JikanAnimeDataSource
import com.example.jikan.data.AnimeInfo
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
