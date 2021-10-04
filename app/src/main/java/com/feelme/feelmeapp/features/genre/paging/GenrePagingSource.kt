package com.feelme.feelmeapp.features.genre.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.features.genre.usecase.GenreUseCase
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.toMovieDb
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.MovieGenreCrossRef
import com.feelme.feelmeapp.modeldb.toResultApi
import com.feelme.feelmeapp.utils.ResponseApi
import okhttp3.internal.toImmutableList
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
                nextKey = if(response.isEmpty()) null else page.plus(1)
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

                if(page < 3) {
                    list?.let {
                        val movieDb: MutableList<Movie> = mutableListOf()
                        val movieGenreDb: MutableList<MovieGenreCrossRef> = mutableListOf()
                        it?.results?.forEach { Result ->
                            movieDb.add(Result.toMovieDb())
                            movieGenreDb.add(MovieGenreCrossRef(Result.id, genreId))
                        }

                        genreRepository.saveMovieCategoryDb(movieGenreDb.toList())
                        genreRepository.saveMovieDb(movieDb.toList())
                    }
                }

                genreUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                val moviesDb = genreRepository.getMovieDb(genreId, 20, page - 1)
                val moviesApi: MutableList<Result> = mutableListOf()

                if(moviesDb.movie.isEmpty()) return listOf()
                else {
                    moviesDb.movie.forEach {
                        moviesApi.add(it.toResultApi())
                    }

                    return moviesApi.toImmutableList()
                }
            }
        }
    }
}