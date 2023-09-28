package com.example.network.di

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
    fun getNetWorkClient() : com.example.network.AnimeService = com.example.network.NetworkClient

}