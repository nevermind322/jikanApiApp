package com.example.jikan.data.repos

import com.example.jikan.data.datasources.ApiResponse
import com.example.jikan.data.datasources.JikanAnimeDataSource
import com.example.jikan.data.AnimeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository(private val jikanAnimeDataSource: JikanAnimeDataSource) {

    suspend fun getAnimeById(id: Int): ApiResponse<AnimeInfo> =
        withContext(Dispatchers.IO) { jikanAnimeDataSource.getAnime(id) }


    suspend fun getTopAnime(): ApiResponse<List<AnimeInfo>> =
        withContext(Dispatchers.IO) { jikanAnimeDataSource.getTopAnime() }
}
