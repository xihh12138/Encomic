package com.xihh.base.data

import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

fun <Item : Any> CoroutineScope.createIntPagingFlow(fetch: suspend (Int) -> Result<List<Item>>): Flow<PagingData<Item>> {
    val pager = Pager(PagingConfig(20)) { IntPagingSource(fetch) }
    return pager.flow.cachedIn(this)
}

private class IntPagingSource<Item : Any>(val fetchData: suspend (Int) -> Result<List<Item>>) :
    PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val pageIndex = params.key ?: 1
        val response = fetchData(pageIndex)
        return if (response.isSuccess) {
            val data = response.data ?: emptyList()
            val preKey = if (pageIndex == 1) null else pageIndex - 1
            val nextKey = if (data.isEmpty()) null else pageIndex + 1

            LoadResult.Page(data, preKey, nextKey)
        } else {
            LoadResult.Error(RuntimeException(response.messageOrDefault))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}