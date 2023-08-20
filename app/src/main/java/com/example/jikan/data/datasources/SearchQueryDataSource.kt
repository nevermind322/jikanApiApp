package com.example.jikan.data.datasources

import com.example.jikan.db.AppDb
import com.example.jikan.db.Entities.SearchQueryDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchQueryDataSource @Inject constructor(private val db: AppDb) {

    suspend fun getAll(): List<String> =
        withContext(Dispatchers.IO) { db.searchQueryDao().getAll().map { it.query } }

    suspend fun deleteQuery(query: String) {
        withContext(Dispatchers.IO) {
            db.searchQueryDao().delete(SearchQueryDbModel(query))
        }
    }

    suspend fun addQuery(query: String) {
        withContext(Dispatchers.IO) {
            db.searchQueryDao().insertAll(SearchQueryDbModel(query))
        }
    }
}