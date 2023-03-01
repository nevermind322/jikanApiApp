package com.example.jikan

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository(private val jikanAnimeDataSource: JikanAnimeDataSource) {

    suspend fun getAnimeById(id: Int): ApiResponse = withContext(Dispatchers.IO) { jikanAnimeDataSource.getAnime(id) }
    }
