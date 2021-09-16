package com.feelme.feelmeapp.features.movieDetails.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class MovieDetailsRepository: BaseRepository() {
    suspend fun getMovieById(id: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }
    }

    suspend fun getMovieStreaming(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieStreaming(movieId)
        }
    }
}