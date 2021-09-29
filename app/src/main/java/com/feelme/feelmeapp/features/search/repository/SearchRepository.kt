package com.feelme.feelmeapp.features.search.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.utils.ResponseApi

class SearchRepository(private val context: Context) : BaseRepository() {
    suspend fun getSearch(query: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.search(query)
        }
    }

    suspend fun searchMovieDb(query: String): List<Movie> {
        return FeelMeDatabase.getDatabase(context).movieDao().getMovieByQuery(query)
    }
}