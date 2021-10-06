package com.feelme.feelmeapp.adapters.PagingMovieGridAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

class PagedMovieGridPagingSource(
    val callbackItens: suspend (page: Int) -> List<PagedMovieGridModel>
) : PagingSource<Int, PagedMovieGridModel>() {
    override fun getRefreshKey(state: PagingState<Int, PagedMovieGridModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedMovieGridModel> {
        return try {
            val page: Int = params.key ?: 1
            val response = callbackItens(page)

            LoadResult.Page(
                data = response,
                prevKey = if(page == 1) null else -1,
                nextKey = if(response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}