package com.example.jikan.data.datasources

import com.example.jikan.data.AnimeInfo
import com.example.jikan.utils.NetworkClient
import java.lang.Exception

class JikanAnimeDataSource {
    fun  getAnime(id: Int): ApiResponse<AnimeInfo> {

        val call = NetworkClient.findAnimeById(id)
        val serverResponse = call.execute()
        if (serverResponse.isSuccessful) {
            val infoJson = serverResponse.body()
            val info = try {
                AnimeInfo(infoJson!!.data.title, infoJson.data.images.jpg.imageUrl!!, infoJson.data.synopsis!!)
            }
            catch (e : Throwable){ return ApiResponse.Error(e)
            }
            return ApiResponse.Success(info)
        }
        return ApiResponse.Error(Exception("Unsuccessful call: ${serverResponse.code()}"))
    }

    fun getTopAnime() : ApiResponse<List<AnimeInfo>> {

        val call = NetworkClient.getTopAnime()
        val serverResponse = call.execute()

        if(serverResponse.isSuccessful){
            val infoJson = serverResponse.body()
            val infoList = try {
                infoJson!!.data.map { AnimeInfo(it.title, it.images.jpg.imageUrl, it.synopsis ?: "") }
            }
            catch (e: Throwable) {return ApiResponse.Error(e)
            }
            return ApiResponse.Success(infoList)
        }
        return ApiResponse.Error(Exception("Unsuccessful call: ${serverResponse.code()}"))
    }

}


sealed class ApiResponse<T>{
    data class Success<T>(val info : T) : ApiResponse<T>()
    data class Error<T>(val error : Throwable) : ApiResponse<T>()
}