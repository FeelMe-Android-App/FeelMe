package com.feelme.feelmeapp.features.home.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.utils.ResponseApi

class HomeUseCase {

    private val homeRepository = HomeRepository()

    suspend fun getNowPlayingMovies() {

        when (val responseApi = homeRepository.getNowPlayingMovies()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as NowPlaying
                val result = data?.results?.map {
                    it.backdrop_path = it.backdrop_path.getFullImageUrl()
                    it.poster_path = it.poster_path.getFullImageUrl()
                }
                result
            }
            is ResponseApi.Error -> {

            }
        }


    }
}