package com.example.jikan.data.repos

import com.example.jikan.data.datasources.SearchQueryDataSource
import com.example.jikan.db.Entities.SearchQuery

class SearchQueryRepo(val searchQueryDataSource: SearchQueryDataSource) {

    suspend fun getAll() : List<String> = searchQueryDataSource.getAll()

    suspend fun deleteQuery(query : String) {
        searchQueryDataSource.deleteQuery(query)
    }

    suspend fun addQuery(query : String){
        searchQueryDataSource.addQuery(query)
    }

}