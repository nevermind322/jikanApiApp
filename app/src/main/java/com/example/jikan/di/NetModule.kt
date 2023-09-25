package com.example.jikan.di

import com.example.jikan.utils.AnimeService
import com.example.jikan.utils.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Singleton
    @Provides
    fun getNetWorkClient() : AnimeService = NetworkClient

}