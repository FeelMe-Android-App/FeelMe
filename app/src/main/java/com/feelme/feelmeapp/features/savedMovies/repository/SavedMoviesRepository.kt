package com.feelme.feelmeapp.features.savedMovies.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class SavedMoviesRepository: BaseRepository() {
    suspend fun getSavedMovies(page: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getUnwatchedMovies(page)
        }
    }
}