package com.feelme.feelmeapp.features.profile.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class ProfileRepository() : BaseRepository() {
    suspend fun getMyProfile(): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getMyProfile()
        }
    }
}