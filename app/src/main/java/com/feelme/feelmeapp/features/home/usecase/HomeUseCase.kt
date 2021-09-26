package com.feelme.feelmeapp.features.home.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.home.repository.HomeRepository
import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.modeldb.*
import com.feelme.feelmeapp.modeldb.Genre
import com.feelme.feelmeapp.utils.ResponseApi
import okhttp3.internal.toImmutableList

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
                    val nowPlayingDb: MutableList<com.feelme.feelmeapp.modeldb.NowPlaying> = mutableListOf()

                    Result.forEach { Movie ->
                        nowPlayingDb.add(Movie.toNowPlayingDb())
                    }

                    this.homeRepository.saveMovieList(nowPlayingDb)
                }

                return ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                val nowPlayingDb = this.homeRepository.getMovieListDb()
                if(nowPlayingDb.isNullOrEmpty()) return responseApi

                val movies = nowPlayingDb.map { it.toResultApi() }
                return ResponseApi.Success(movies.toImmutableList())
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