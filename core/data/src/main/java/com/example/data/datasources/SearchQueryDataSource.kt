package com.example.data.datasources

import com.example.database.Dao.QueryDao
import com.example.database.Entities.SearchQueryDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchQueryDataSource @Inject constructor(private val searchDao: QueryDao) {

    fun getAll(): Flow<List<String>> = searchDao.getAll().map { it.map { elem -> elem.query } }


    suspend fun deleteQuery(query: String) = withContext(Dispatchers.IO) {
        searchDao.delete(SearchQueryDbModel(query))
    }


    suspend fun addQuery(query: String) = withContext(Dispatchers.IO) {
        searchDao.insertAll(SearchQueryDbModel(query))
    }
}