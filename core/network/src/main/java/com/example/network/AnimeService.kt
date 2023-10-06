package com.example.network

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AnimeService {
    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<AnimeJson>

    @GET("anime")
    suspend fun searchAnimeByName(
        @Query("q") name: String,
        @QueryMap queries: Map<String, String>
    ): Response<PagedAnimeResponse>

    @GET("top/anime")
    suspend fun getTopAnime(@QueryMap queries: Map<String, String>): Response<PagedAnimeResponse>
}

object NetworkClient : AnimeService{

    private val gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://api.jikan.moe/v4/")
        .build()

    private val apiService = retrofit.create(AnimeService::class.java)

    override suspend fun getAnimeById(id: Int) =
        apiService.getAnimeById(id)

    override suspend fun searchAnimeByName(name: String, queries: Map<String, String>) =
        apiService.searchAnimeByName(name, queries)

    override suspend fun getTopAnime(queries: Map<String, String>) =
        apiService.getTopAnime(queries)

}

