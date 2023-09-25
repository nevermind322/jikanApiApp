package com.example.jikan.di

import android.content.Context
import androidx.room.Room
import com.example.jikan.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun getDb( @ApplicationContext context: Context) : AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "databaseClass").build()
    }

    @Provides
    fun getSearchDao(db: AppDb) = db.searchQueryDao()
}