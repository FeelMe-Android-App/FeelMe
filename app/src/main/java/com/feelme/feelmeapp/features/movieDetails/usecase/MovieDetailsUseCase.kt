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
                data.poster_path = data.poster_path.getFullImageUrl()
                return ResponseApi.Success(data)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }

    suspend fun getMovieStreamings(movieId: Int): ResponseApi {
        when(val responseApi = movieDetailsRepository.getMovieStreamings(movieId)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as MovieStreamings
                var streamingsList: List<Flatrate> = listOf()

                if(data.results.BR !== null) {
                    data.results.BR.flatrate?.let {
                        streamingsList = it.map { Item ->
                            Item.logo_path.let {
                                Item.logo_path = it.getFullImageUrl()
                            }
                            Item
                        }
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