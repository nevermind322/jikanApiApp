package com.example.model


data class AnimeInfo(
    val id: Int,
    val Title: String,
    val imageUrl: String?,
    val synopsis: String,
    val genres: List<String>
)
