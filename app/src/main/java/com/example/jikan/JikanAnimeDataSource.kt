package com.example.jikan

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JikanAnimeDataSource {
    fun getAnime(id: Int): ApiResponse {

        val call = NetworkClient.findAnimeById(id)
        val res = call.execute()
        if (res.isSuccessful) {
            val infoJson = res.body()
            val info = try {
                AnimeInfo(infoJson!!.data.title, infoJson.data.images.jpg.imageUrl!!, infoJson.data.synopsis!!)
            }
            catch (e : Throwable){ return ApiResponse.Error(e)}
            return  ApiResponse.Success(info)
        }
        return ApiResponse.Error(java.lang.Exception("Unsuccessful call: ${res.code()}"))
    }

}


sealed class ApiResponse{
    data class Success(val info : AnimeInfo) : ApiResponse()
    data class Error(val error : Throwable) : ApiResponse()
}