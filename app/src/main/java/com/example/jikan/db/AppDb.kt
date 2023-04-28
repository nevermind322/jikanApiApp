package com.example.jikan.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jikan.db.Dao.QueryDao
import com.example.jikan.db.Entities.SearchQuery

@Database(entities = [SearchQuery::class], version = 1)
abstract class AppDb : RoomDatabase(){
    abstract fun searchQueryDao() : QueryDao
}