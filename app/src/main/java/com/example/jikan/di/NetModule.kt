package com.example.jikan.di

import com.example.jikan.utils.AnimeService
import com.example.jikan.utils.NetworkClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    fun getNetWorkClient() : AnimeService = NetworkClient

}