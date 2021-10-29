package com.feelme.feelmeapp.adapters.PagingMovieComments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

class PagedMovieCommentsPagingSource(
    val callbackItems: suspend (page: Int) -> List<PagedMovieCommentsModel>
) : PagingSource<Int, PagedMovieCommentsModel>() {
    override fun getRefreshKey(state: PagingState<Int, PagedMovieCommentsModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedMovieCommentsModel> {
        return try {
            val page: Int = params.key ?: 1
            val response = callbackItems(page)

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