package com.example.data.datasources
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.AnimeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class JikanPagingDataSource (
    private val  netClient : AnimeService,
    private val query: String,
    private val queries: Map<String, String> = mutableMapOf()
) : PagingSource<Int, com.example.model.AnimeInfo>() {


    override fun getRefreshKey(state: PagingState<Int, com.example.model.AnimeInfo>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.example.model.AnimeInfo> =
        withContext(Dispatchers.IO) {
            when (query) {
                "" -> LoadResult.Page(data = listOf(), prevKey = null, nextKey = null)
                else -> {

                    val nextPageNumber = params.key ?: 1
                    val mutableQueries = queries.toMutableMap()

                    mutableQueries += "page" to nextPageNumber.toString()
                    mutableQueries += "limit" to params.loadSize.toString()

                    val serverResponse =
                        netClient.searchAnimeByName(query, mutableQueries).execute()

                    if (serverResponse.isSuccessful)
                        try {
                            val responseBody =
                                serverResponse.body() ?: throw java.lang.Exception("Server error")

                            LoadResult.Page(
                                data = responseBody.data.map {
                                    com.example.model.AnimeInfo(
                                        it.malId,
                                        it.title,
                                        it.images.jpg.imageUrl,
                                        it.synopsis ?: ""
                                    )
                                },
                                prevKey = if (params.key != 1) params.key?.minus(1) else null,
                                nextKey = if (responseBody.pagination.hasNextPage) nextPageNumber + 1 else null
                            )

                        } catch (e: Throwable) {
                            LoadResult.Error(e)
                        }
                    else
                        LoadResult.Error(java.lang.Exception("Unsuccessful call: ${serverResponse.code()}"))
                }
            }
        }
}