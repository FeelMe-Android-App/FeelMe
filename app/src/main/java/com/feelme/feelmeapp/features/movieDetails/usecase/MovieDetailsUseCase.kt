package com.feelme.feelmeapp.features.movieDetails.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.model.Flatrate
import com.feelme.feelmeapp.model.Movie
import com.feelme.feelmeapp.model.MovieStreamings
import com.feelme.feelmeapp.utils.ResponseApi

class MovieDetailsUseCase {
    private val movieDetailsRepository = MovieDetailsRepository()

    suspend fun getMovieById(id: Int): ResponseApi {
        when(val responseApi = movieDetailsRepository.getMovieById(id)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as Movie
                data.posterPath?.let { data.posterPath = it.getFullImageUrl() }
                return ResponseApi.Success(data)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }

    suspend fun getMovieStreamings(movieId: Int): ResponseApi {
        when(val responseApi = movieDetailsRepository.getMovieStreaming(movieId)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as MovieStreamings
                var streamingsList: List<Flatrate> = listOf()

                data?.results?.BR?.flatrate?.let { FlatrateList ->
                    streamingsList = FlatrateList.map { Item ->
                        Item.logoPath?.let { Item.logoPath = it.getFullImageUrl() }
                        Item
                    }
                }

                return ResponseApi.Success(streamingsList)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}