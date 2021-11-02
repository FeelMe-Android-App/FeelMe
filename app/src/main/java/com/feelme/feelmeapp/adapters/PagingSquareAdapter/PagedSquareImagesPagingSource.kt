package com.feelme.feelmeapp.adapters.PagingSquareAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

class PagedSquareImagesPagingSource(
    val callbackItens: suspend (page: Int) -> List<PagedSquareImagesModel>
) : PagingSource<Int, PagedSquareImagesModel>() {
    override fun getRefreshKey(state: PagingState<Int, PagedSquareImagesModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedSquareImagesModel> {
        return try {
            val page: Int = params.key ?: 1
            val response = callbackItens(page)

            LoadResult.Page(
                data = response,
                prevKey = if(page == 1) null else page.minus(1),
                nextKey = if(response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}