package com.example.jikan.data.datasources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jikan.data.AnimeInfo
import com.example.jikan.utils.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JikanPagingDataSource(private val query: String, private val queries: MutableMap<String, String> = mutableMapOf()) :
    PagingSource<Int, AnimeInfo>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeInfo> = withContext(Dispatchers.IO){
        val nextPageNumber = params.key ?: 1
        queries += "page" to nextPageNumber.toString()
        queries +=  "limit" to params.loadSize.toString()
        val serverResponse = NetworkClient.getAnimeByName(
            query,
            queries
        ).execute()
        val response: LoadResult<Int, AnimeInfo> = if (serverResponse.isSuccessful)
            try {
                val responseBody =
                    serverResponse.body() ?: throw java.lang.Exception("Server error")

                LoadResult.Page(
                    data = responseBody.data.map {
                        AnimeInfo(
                            it.title,
                            it.images.jpg.imageUrl,
                            it.synopsis ?: ""
                        )
                    },
                    prevKey = if (params.key != 1) params.key?.minus(1) else null ,
                    nextKey = if (responseBody.pagination.hasNextPage) nextPageNumber + 1 else null
                )

            } catch (e: Throwable) {
                LoadResult.Error(e)
            }
        else
            LoadResult.Error(java.lang.Exception("Unsuccessful call: ${serverResponse.code()}"))
        return@withContext response
    }


}