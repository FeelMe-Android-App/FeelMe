package com.feelme.feelmeapp.features.whatToWatch.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.whatToWatch.repository.WhatToWatchRepository
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.utils.ResponseApi

class WhatToWatchUseCase {
    private val whatToWatchRepository = WhatToWatchRepository()

    suspend fun getDiscoverMovies(providers: String, genres: String): ResponseApi {
        return when(val responseApi = whatToWatchRepository.getDiscoverMovies(providers, genres)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as DiscoverMovies
                val results = data.results.map { Result ->
                    Result.posterPath?.let { Result.posterPath = it.getFullImageUrl() }
                    Result.backdropPath?.let { Result.backdropPath = it.getFullImageUrl() }
                    Result
                }.filter { it.overview.isNotEmpty() }.take(10)

                ResponseApi.Success(results)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }
}