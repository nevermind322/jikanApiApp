package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.Dao.QueryDao
import com.example.database.Entities.SearchQueryDbModel

@Database(entities = [SearchQueryDbModel::class], version = 1)
abstract class AppDb : RoomDatabase(){
    abstract fun searchQueryDao() : QueryDao
}