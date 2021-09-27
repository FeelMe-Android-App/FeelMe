package com.feelme.feelmeapp.features.home.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.modeldb.*
import com.feelme.feelmeapp.modeldb.Genre
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.utils.ResponseApi
import okhttp3.internal.toImmutableList
import kotlin.Result

class HomeUseCase(private val homeRepository: HomeRepository) {
    suspend fun getNowPlayingMovies(): ResponseApi {

        when (val responseApi = homeRepository.getNowPlayingMovies()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? NowPlaying
                val result = data?.results?.map { Result ->
                    Result.backdropPath?.let { Result.backdropPath = it.getFullImageUrl() }
                    Result.posterPath?.let { Result.posterPath = it.getFullImageUrl() }
                    Result
                }

                result?.let { Result ->
                    val nowPlayingDb: MutableList<Movie> = mutableListOf()
                    val movieNowPlaying: MutableList<MovieNowPlaying> = mutableListOf()

                    Result.forEach { MovieResult ->
                        nowPlayingDb.add(MovieResult.toMovieDb())
                        movieNowPlaying.add(MovieResult.toMovieNowPlaying())
                    }

                    this.homeRepository.saveMovieDb(nowPlayingDb)
                    this.homeRepository.saveMovieNowPlayingDb(movieNowPlaying)
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {

                val nowPlayingDb = this.homeRepository.getNowPlayingDb()
                if(nowPlayingDb.isNullOrEmpty()) return responseApi

                val movieNowPlayingDb: MutableList<com.feelme.feelmeapp.model.Result> = mutableListOf()

                nowPlayingDb.forEach { MovieAndNowPlaying ->
                    movieNowPlayingDb.add(MovieAndNowPlaying.movie.toResultApi())
                }

                return ResponseApi.Success(movieNowPlayingDb)
            }
        }
    }

    suspend fun getGenres(): ResponseApi {
        when(val responseApi = homeRepository.getGenres()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? Genres
                val result = data?.genres

                result?.let { GenreApi ->
                    val genreDb: MutableList<Genre> = mutableListOf()

                    GenreApi.forEach {
                        genreDb.add(it.toGenreDb())
                    }

                    this.homeRepository.saveGenres(genreDb);
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                val genresDb = this.homeRepository.getGenresDb()
                if(genresDb.isNullOrEmpty()) return responseApi

                val genres = genresDb.map { it.toGenreApi() }
                return ResponseApi.Success(genres.toImmutableList())
            }
        }
    }
}