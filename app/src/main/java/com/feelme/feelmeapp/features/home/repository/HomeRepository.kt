package com.feelme.feelmeapp.features.home.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.Genre
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.MovieAndNowPlaying
import com.feelme.feelmeapp.modeldb.MovieNowPlaying
import com.feelme.feelmeapp.utils.ResponseApi

class HomeRepository(private val context: Context) : BaseRepository() {
    suspend fun getNowPlayingMovies(providers: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getDiscoverMovies(providers = providers, genres = "", sortBy = "popularity.desc", page = 1)
        }
    }

    suspend fun getLastRelease(providers: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getDiscoverMovies(providers = providers, genres = "", sortBy = "release_date.desc", page = 1)
        }
    }

    suspend fun getGenres(): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getGenres()
        }
    }

    suspend fun saveGenres(genresList: List<Genre>) {
        FeelMeDatabase.getDatabase(context).genreDao().insertAll(genresList)
    }

    suspend fun getGenresDb(): List<Genre> {
        return FeelMeDatabase.getDatabase(context).genreDao().getAllGenres()
    }

    suspend fun saveMovieDb(movieList: List<Movie>) {
        FeelMeDatabase.getDatabase(context).movieDao().insertAll(movieList)
    }

    suspend fun saveMovieNowPlayingDb(nowPlayingList: List<MovieNowPlaying>) {
        FeelMeDatabase.getDatabase(context).movieNowPlaying().insertAllMovies(nowPlayingList)
    }

    suspend fun getNowPlayingDb(): List<MovieAndNowPlaying> {
        return FeelMeDatabase.getDatabase(context).nowPlaying().getNowPlaying()
    }
}