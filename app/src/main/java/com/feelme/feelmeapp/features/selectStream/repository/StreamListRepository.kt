package com.feelme.feelmeapp.features.selectStream.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class StreamListRepository: BaseRepository() {
    suspend fun getStreamList(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getStreamList()
        }
    }
}