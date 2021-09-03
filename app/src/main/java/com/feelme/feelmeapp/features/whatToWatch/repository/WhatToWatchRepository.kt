package com.feelme.feelmeapp.features.whatToWatch.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class WhatToWatchRepository: BaseRepository() {
    suspend fun getDiscoverMovies(providers: String, genres: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getDiscoverMovies(providers, genres, "popularity.desc")
        }
    }
}