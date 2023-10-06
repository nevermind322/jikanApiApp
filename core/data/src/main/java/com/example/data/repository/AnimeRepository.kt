package com.example.data.repository

import com.example.data.datasources.AnimeCacheDataSource
import com.example.data.datasources.ApiResponse
import com.example.data.datasources.JikanAnimeDataSource
import com.example.model.AnimeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val jikanAnimeDataSource: JikanAnimeDataSource,
    private val animeCacheDataSource: AnimeCacheDataSource
) {

    private var topCache: ApiResponse<List<AnimeInfo>>? = null

    suspend fun getAnimeById(id: Int): ApiResponse<AnimeInfo> =
        withContext(Dispatchers.IO) {
            val cache = animeCacheDataSource.findAnimeById(id)
            if (cache != null) ApiResponse.Success(cache)
            else jikanAnimeDataSource.getAnime(id).also {
                when (it) {
                    is ApiResponse.Success -> animeCacheDataSource.addAnime(listOf(it.info))
                    else -> {}
                }
            }
        }


    suspend fun getTopAnime(): ApiResponse<List<AnimeInfo>> =
        withContext(Dispatchers.IO) {
            if (topCache == null) {
                val res = jikanAnimeDataSource.getTopAnime()
                topCache = res
                res
            } else topCache!!
        }
}
