package com.example.data.datasources

import com.example.model.AnimeInfo
import javax.inject.Inject

class AnimeCacheDataSource @Inject constructor(){
    private val cache = HashMap<Int, AnimeInfo>()

    fun addAnime(list: Collection<AnimeInfo>) {
        for (elem in list) {
            if (elem.id !in cache) cache[elem.id] = elem
        }
    }

    fun findAnimeById(id : Int) = cache[id]


}