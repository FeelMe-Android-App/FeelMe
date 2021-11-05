package com.feelme.feelmeapp.features.movieDetails.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.modeldb.*
import com.feelme.feelmeapp.utils.ResponseApi

class MovieDetailsRepository(private val context: Context): BaseRepository() {
    suspend fun getMovieById(id: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }
    }

    suspend fun getMovieStatusId(id: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getMovieStatus(id)
        }
    }

    suspend fun saveUnwatchedMovie(movieId: Int, movieDetails: FeelMeMovie): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.saveUnwatchedMovie(movieId, movieDetails)
        }
    }

    suspend fun saveWatchedMovie(movieId: Int, movieDetails: FeelMeMovie): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.saveWatchedMovie(movieId, movieDetails)
        }
    }

    suspend fun removeMovie(movieId: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.removeMovie(movieId)
        }
    }

    suspend fun voteMovie(feelingId: Int, movieId: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.voteMovie(feelingId, movieId)
        }
    }

    suspend fun postMovieComment(movieId: Int, comment: FeelMeMovieComment): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.postMovieComment(movieId, comment)
        }
    }

    suspend fun getMovieStreaming(movieId: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieStreaming(movieId)
        }
    }

    suspend fun getMovieComments(movieId: Int): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.getMovieComments(movieId)
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

    suspend fun deleteComment(commentId: String): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.deleteComment(commentId)
        }
    }
}