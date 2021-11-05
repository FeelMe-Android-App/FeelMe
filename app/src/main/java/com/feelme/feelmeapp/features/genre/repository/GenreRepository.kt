package com.feelme.feelmeapp.features.genre.repository

import android.content.Context
import com.feelme.feelmeapp.api.ApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.modeldb.GenreMovie
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.MovieGenreCrossRef
import com.feelme.feelmeapp.utils.ResponseApi

class GenreRepository(private val context: Context): BaseRepository() {
    suspend fun getMoviesByGenre(providers: String, genreId: Int, page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getDiscoverMovies(providers = providers, genres = genreId.toString(), sortBy = "popularity.desc", page = page)
        }
    }

    suspend fun saveMovieDb(movieList: List<Movie>) {
        FeelMeDatabase.getDatabase(context).movieDao().insertAll(movieList)
    }

    suspend fun saveMovieCategoryDb(moviesGenresList: List<MovieGenreCrossRef>) {
        FeelMeDatabase.getDatabase(context).movieGenreDao().insertAllMovieGenre(moviesGenresList)
    }

    suspend fun getMovieDb(genreId: Int, limit: Int, offset: Int): GenreMovie {
        return FeelMeDatabase.getDatabase(context).genreDao().getMovieByGenre(genreId, limit, offset)
    }
}