package com.feelme.feelmeapp.features.movieDetails.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.Genre
import com.feelme.feelmeapp.modeldb.*
import com.feelme.feelmeapp.utils.ResponseApi
import okhttp3.internal.toImmutableList

class MovieDetailsUseCase(private val movieDetailsRepository: MovieDetailsRepository) {

    suspend fun getMovieById(id: Int): ResponseApi {
        when(val responseApi = movieDetailsRepository.getMovieById(id)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as Result
                data.posterPath?.let { data.posterPath = it.getFullImageUrl() }

                data?.let { MovieResult ->
                    this.movieDetailsRepository.saveMovieDb(MovieResult.toMovieDb())

                    val movieGenreDb: MutableList<MovieGenreCrossRef> = mutableListOf()

                    MovieResult.genreIds.forEach { Genre ->
                        movieGenreDb.add(MovieGenreCrossRef(movieId = MovieResult.id, genreId = Genre.id))
                    }

                    this.movieDetailsRepository.saveMovieGenreDb(movieGenreDb)
                }

                return ResponseApi.Success(data)
            }
            is ResponseApi.Error -> {
                val movieStreamGenre = this.movieDetailsRepository.getMovieGenre(id)
                if(movieStreamGenre.movie.title.isNullOrEmpty()) return responseApi

                val genres = movieStreamGenre.genre.map {
                    it.toGenreApi()
                }

                val movie = movieStreamGenre.movie.toResultApi()
                movie.genreIds = genres

                return ResponseApi.Success(movie)
            }
        }
    }

    suspend fun getMovieStreamings(movieId: Int): ResponseApi {
        when(val responseApi = movieDetailsRepository.getMovieStreaming(movieId)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as MovieStreamings
                var streamingsList: List<Flatrate> = listOf()
                var movieStreamDb: MutableList<MovieStreamCrossRef> = mutableListOf()

                data?.results?.BR?.flatrate?.let { FlatrateList ->
                    streamingsList = FlatrateList.map { Item ->
                        Item.logoPath?.let { Item.logoPath = it.getFullImageUrl() }
                        Item
                    }

                    streamingsList.forEach {
                        movieStreamDb.add(MovieStreamCrossRef(movieId = movieId, providerId = it.providerId))
                    }

                    this.movieDetailsRepository.saveMovieStreamDb(movieStreamDb)
                }

                return ResponseApi.Success(streamingsList)
            }
            is ResponseApi.Error -> {
                val movieStream = this.movieDetailsRepository.getMovieStreamDb(movieId)
                if(movieStream.movie.title.isNullOrEmpty()) return responseApi

                val stream = movieStream.stream.map {
                    it.toFlatrate()
                }

                return ResponseApi.Success(stream)
            }
        }
    }
}