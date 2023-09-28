package com.example.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.Entities.SearchQueryDbModel
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