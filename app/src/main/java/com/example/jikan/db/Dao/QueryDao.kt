package com.example.jikan.db.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikan.db.Entities.SearchQueryDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface QueryDao {
    @Query("SElECT * FROM SearchQuery")
    fun getAll() : Flow<List<SearchQueryDbModel>>

    @Delete
    suspend fun delete(query: SearchQueryDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg queries : SearchQueryDbModel)
}