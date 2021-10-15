package com.feelme.feelmeapp.features.searchFriend.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class SearchFriendRepository(): BaseRepository() {
    suspend fun getFriendSearch(query: String, page: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getSearchFriend(query, page)
        }
    }
}