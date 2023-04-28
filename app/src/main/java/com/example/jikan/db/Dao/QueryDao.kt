package com.example.jikan.db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikan.db.Entities.SearchQuery

@Dao
interface QueryDao {
    @Query("SElECT * FROM SearchQuery")
    suspend fun getAll() : List<SearchQuery>

    @Delete
    suspend fun delete(query: SearchQuery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg queries : SearchQuery)
}