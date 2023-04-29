package com.example.jikan.data.datasources

import com.example.jikan.db.AppDb
import com.example.jikan.db.Entities.SearchQuery

class SearchQueryDataSource(private val db : AppDb) {

    suspend fun getAll() : List<String> = db.searchQueryDao().getAll().map { it.query }

    suspend fun deleteQuery(query : String) {
        db.searchQueryDao().delete(SearchQuery(query))
    }

    suspend fun addQuery(query : String){
        db.searchQueryDao().insertAll(SearchQuery(query))
    }
}