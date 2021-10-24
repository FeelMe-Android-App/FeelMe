package com.feelme.feelmeapp.features.userProfile.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class UserProfileRepository() : BaseRepository() {
    suspend fun getUserProfile(uid: String): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getUserProfile(uid)
        }
    }
}