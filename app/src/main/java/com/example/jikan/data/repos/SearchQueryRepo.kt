package com.example.jikan.data.repos

import com.example.jikan.data.datasources.SearchQueryDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchQueryRepo @Inject constructor(private val searchQueryDataSource: SearchQueryDataSource) {

    fun getAll(): Flow<List<String>> = searchQueryDataSource.getAll()

    suspend fun deleteQuery(query: String) = searchQueryDataSource.deleteQuery(query)


    suspend fun addQuery(query: String) = searchQueryDataSource.addQuery(query)


}