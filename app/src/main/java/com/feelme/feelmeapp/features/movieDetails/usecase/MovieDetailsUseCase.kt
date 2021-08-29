package com.feelme.feelmeapp.features.movieDetails.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.model.Movie
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

}