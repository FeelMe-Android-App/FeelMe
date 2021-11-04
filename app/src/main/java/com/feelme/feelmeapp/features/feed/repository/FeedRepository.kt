package com.feelme.feelmeapp.features.feed.repository

import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class FeedRepository() : BaseRepository() {
    suspend fun getFriendsStatus(): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getFriendsStatus()
        }
    }

    suspend fun getFriendsComments(page: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getFriendsComments(page)
        }
    }

    suspend fun getUserFollow(): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getUserFollow()
        }
    }
}