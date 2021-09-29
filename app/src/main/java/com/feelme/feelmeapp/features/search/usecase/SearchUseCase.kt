package com.feelme.feelmeapp.features.search.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.search.repository.SearchRepository
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.Search
import com.feelme.feelmeapp.model.toMovieDb
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.toResultApi
import com.feelme.feelmeapp.utils.ResponseApi

class SearchUseCase(private val searchRepository: SearchRepository) {
    suspend fun getSearch(query: String): ResponseApi {
        when(val responseApi = searchRepository.getSearch(query)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? Search
                val result = data?.results?.map { Result ->
                    Result.backdropPath?.let { Result.backdropPath = it.getFullImageUrl() }
                    Result.posterPath?.let { Result.posterPath = it.getFullImageUrl() }
                    Result
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                val movieDbQuery = this.searchRepository.searchMovieDb(query)
                if(movieDbQuery.isNullOrEmpty()) return responseApi

                val movieApi: MutableList<Result> = mutableListOf()

                movieDbQuery.forEach {
                    movieApi.add(it.toResultApi())
                }

                return ResponseApi.Success(movieApi)
            }
        }
    }
}