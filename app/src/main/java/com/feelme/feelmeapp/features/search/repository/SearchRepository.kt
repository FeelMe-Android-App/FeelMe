package com.feelme.feelmeapp.features.search.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class SearchRepository : BaseRepository() {
    suspend fun getSearch(query: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.search(query)
        }
    }
}