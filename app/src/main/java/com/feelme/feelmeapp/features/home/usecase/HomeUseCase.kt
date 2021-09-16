package com.feelme.feelmeapp.features.home.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.utils.ResponseApi

class HomeUseCase {

    private val homeRepository = HomeRepository()

    suspend fun getNowPlayingMovies(): ResponseApi {

        when (val responseApi = homeRepository.getNowPlayingMovies()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? NowPlaying
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
}