package com.feelme.feelmeapp.features.watchedMovies.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class WatchedMoviesRepository: BaseRepository() {
    suspend fun getWatchedMovies(page:Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getWatchedMovies(page)
        }
    }
}