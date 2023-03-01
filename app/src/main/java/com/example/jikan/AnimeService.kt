package com.example.jikan

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {
    @GET("anime/{id}")
    fun getAnimeById(@Path("id") id: Int): Call<AnimeJson>
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

}