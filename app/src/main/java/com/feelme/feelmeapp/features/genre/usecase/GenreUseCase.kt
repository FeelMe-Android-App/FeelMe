package com.feelme.feelmeapp.features.genre.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.toMovieDb
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.utils.ResponseApi

class GenreUseCase(private val genreRepository: GenreRepository) {
    suspend fun getMoviesByGenre(categoryId: Int): ResponseApi {
        when(val responseApi = genreRepository.getMoviesByGenre(categoryId, 1)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? DiscoverMovies
                val result = data?.results?.map { Result ->
                    Result.backdropPath?.let { Result.backdropPath = it.getFullImageUrl() }
                    Result.posterPath?.let { Result.posterPath = it.getFullImageUrl() }
                    Result
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }

    suspend fun setupMoviesList(list: DiscoverMovies?): List<Result> {
        return list?.results?.map {
            it.backdropPath = it.backdropPath?.getFullImageUrl()
            it.posterPath = it.posterPath?.getFullImageUrl()
            it
        } ?: listOf()
    }
}