package com.feelme.feelmeapp.features.movieDetails.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.*
import com.feelme.feelmeapp.utils.ResponseApi

class MovieDetailsRepository(private val context: Context): BaseRepository() {
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

    suspend fun saveMovieDb(movie: Movie) {
        FeelMeDatabase.getDatabase(context).movieDao().insert(movie)
    }

    suspend fun saveMovieGenreDb(movieGenre: List<MovieGenreCrossRef>) {
        FeelMeDatabase.getDatabase(context).movieGenreDao().insertAllMovieGenre(movieGenre)
    }

    suspend fun saveMovieStreamDb(movieStream: List<MovieStreamCrossRef>) {
        FeelMeDatabase.getDatabase(context).movieStreamDao().insertAllMovies(movieStream)
    }

    suspend fun getMovieGenre(movieId: Int): MovieGenre {
        return FeelMeDatabase.getDatabase(context).movieDao().getMovieGenre(movieId)
    }

    suspend fun getMovieStreamDb(movieId: Int): MovieStream {
        return FeelMeDatabase.getDatabase(context).movieDao().getMovieStream(movieId)
    }
}