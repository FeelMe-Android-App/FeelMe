package com.feelme.feelmeapp.features.home.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class HomeRepository : BaseRepository() {
    suspend fun getNowPlayingMovies(): ResponseApi {

        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies()
        }

    }


}