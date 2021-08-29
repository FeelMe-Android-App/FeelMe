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
                val result = data?.results?.map {
                    if(!it.backdrop_path.isNullOrEmpty()) it.backdrop_path = it.backdrop_path.getFullImageUrl()
                    if(!it.poster_path.isNullOrEmpty()) it.poster_path = it.poster_path.getFullImageUrl()
                    it
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}