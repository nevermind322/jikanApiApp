package com.example.data

import com.example.model.AnimeInfo
import com.example.network.AnimeJson

internal fun mapToModel(json: AnimeJson.Data): AnimeInfo = AnimeInfo(id = json.malId,
    Title = json.title,
    imageUrl = json.images.jpg.imageUrl,
    synopsis = json.synopsis ?: "",
    genres = json.genres.map { it.name })