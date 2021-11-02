package com.feelme.feelmeapp.adapters.PagingFriendsAdapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

class PagedFriendsPagingSource(
    val callbackItems: suspend (page: Int) -> List<PagedFriendsModel>
) : PagingSource<Int, PagedFriendsModel>() {
    override fun getRefreshKey(state: PagingState<Int, PagedFriendsModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedFriendsModel> {
        return try {
            val page: Int = params.key ?: 1
            val response = callbackItems(page)

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