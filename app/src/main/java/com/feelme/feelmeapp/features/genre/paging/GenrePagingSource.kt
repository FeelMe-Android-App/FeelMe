package com.feelme.feelmeapp.features.genre.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.features.genre.usecase.GenreUseCase
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.utils.ResponseApi
import java.lang.Exception

class GenrePagingSource(
    val genreRepository: GenreRepository,
    val genreUseCase: GenreUseCase,
    val genreId: Int,
): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page: Int = params.key ?: 1
            val response = callDiscoverMoviesByGenre(genreId, page)

            LoadResult.Page(
                data = response,
                prevKey = if(page == 1) null else -1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun callDiscoverMoviesByGenre(genreId: Int, page: Int): List<Result> {
        return when(
            val response = genreRepository.getMoviesByGenre(genreId, page)
        ) {
            is ResponseApi.Success -> {
                val list = response.data as? DiscoverMovies
                genreUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                listOf()
            }
        }
    }
}