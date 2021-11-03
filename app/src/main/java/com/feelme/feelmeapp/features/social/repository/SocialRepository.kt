package com.feelme.feelmeapp.features.social.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class SocialRepository(): BaseRepository() {
    suspend fun followUser(): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.followUser()
        }
    }
    suspend fun unfollowUser(): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.unfollowUser()
        }
    }
}