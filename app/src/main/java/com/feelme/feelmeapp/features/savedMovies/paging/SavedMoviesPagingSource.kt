package com.feelme.feelmeapp.features.savedMovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import com.feelme.feelmeapp.features.savedMovies.usecase.SavedMoviesUseCase
import com.feelme.feelmeapp.model.MyMoviesList
import com.feelme.feelmeapp.model.MyMoviesListItem
import com.feelme.feelmeapp.model.toMovieDb
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.utils.ResponseApi

class SavedMoviesPagingSource(
    private val savedMoviesRepository: SavedMoviesRepository,
    private val savedMoviesUseCase: SavedMoviesUseCase
): PagingSource<Int, MyMoviesListItem>() {
    override fun getRefreshKey(state: PagingState<Int, MyMoviesListItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyMoviesListItem> {
        return try {
            val page: Int = params.key ?: 1
            val response = callMyMoviesList(page)

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else -1,
                nextKey = if(response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun callMyMoviesList(page: Int): List<MyMoviesListItem> {
        return when(
            val response = savedMoviesRepository.getUnwatchedMovies(page)
        ) {
            is ResponseApi.Success -> {
                val list = response.data as? MyMoviesList
                savedMoviesUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                listOf()
            }
        }
    }
}