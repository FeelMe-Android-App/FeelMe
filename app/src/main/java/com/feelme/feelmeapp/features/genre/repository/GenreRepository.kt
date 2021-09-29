package com.feelme.feelmeapp.features.genre.repository

import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.utils.ResponseApi

class GenreRepository: BaseRepository() {
    suspend fun getMoviesByGenre(genreId: Int, page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getDiscoverMovies(providers = "", genres = genreId.toString(), sortBy = "popularity.desc", page = page)
        }
    }
}