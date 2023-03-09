package com.example.jikan.utils

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AnimeService {
    @GET("anime/{id}")
    fun getAnimeById(@Path("id") id: Int): Call<AnimeJson>

    @GET("anime")
    fun searchAnimeByName(
        @Query("q") name: String,
        @QueryMap queries: Map<String, String>
    ): Call<PagedAnimeResponse>
    @GET("top/anime")
    fun getTopAnime(@QueryMap queries : Map<String, String>) : Call<PagedAnimeResponse>
}
object NetworkClient {

    private val gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://api.jikan.moe/v4/")
        .build()

    private val apiService = retrofit.create(AnimeService::class.java)

    fun findAnimeById(id: Int) =
        apiService.getAnimeById(id)

    fun getAnimeByName(name: String , queries : Map<String, String> = mapOf()) = apiService.searchAnimeByName(name, queries)

    fun getTopAnime(queries : Map<String, String> = mapOf()) = apiService.getTopAnime(queries)

}