package com.feelme.feelmeapp.features.home.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.Genre
import com.feelme.feelmeapp.modeldb.NowPlaying
import com.feelme.feelmeapp.utils.ResponseApi

class HomeRepository(private val context: Context) : BaseRepository() {
    suspend fun getNowPlayingMovies(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getNowPlayingMovies()
        }
    }

    suspend fun getGenres(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getGenres()
        }
    }

    suspend fun saveMovieList(nowPlaying: List<NowPlaying>) {
        FeelMeDatabase.getDatabase(context).nowPlayingDao().insertAll(nowPlaying)
    }

    suspend fun getMovieListDb(): List<NowPlaying> {
        return FeelMeDatabase.getDatabase(context).nowPlayingDao().getAllMovies()
    }

    suspend fun saveGenres(genresList: List<Genre>) {
        FeelMeDatabase.getDatabase(context).genreDao().insertAll(genresList)
    }

    suspend fun getGenresDb(): List<Genre> {
        return FeelMeDatabase.getDatabase(context).genreDao().getAllGenres()
    }
}