package com.feelme.feelmeapp.features.whatToWatch.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.whatToWatch.repository.WhatToWatchRepository
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.utils.ResponseApi

class WhatToWatchUseCase {
    private val whatToWatchRepository = WhatToWatchRepository()

    suspend fun getDiscoverMovies(providers: String, genres: String): ResponseApi {
        when(val responseApi = whatToWatchRepository.getDiscoverMovies(providers, genres)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as DiscoverMovies
                var results = data.results.map {
                        if(!it.poster_path.isNullOrEmpty()) {
                            it.poster_path = it.poster_path.getFullImageUrl()
                        }
                        if(!it.backdrop_path.isNullOrEmpty()) {
                            it.backdrop_path = it.backdrop_path.getFullImageUrl()
                        }
                    it
                }.filter { !it.overview.isNullOrEmpty() }.take(10)

                return ResponseApi.Success(results)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}