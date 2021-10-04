package com.feelme.feelmeapp.features.savedMovies.usecase

import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import com.feelme.feelmeapp.model.MyMoviesList
import com.feelme.feelmeapp.model.MyMoviesListItem
import com.feelme.feelmeapp.utils.ResponseApi

class SavedMoviesUseCase(private val savedMoviesRepository: SavedMoviesRepository): BaseRepository() {
    suspend fun getUnwatchedMovies(page: Int): ResponseApi {
        when(val responseApi = savedMoviesRepository.getUnwatchedMovies(page)) {
            is ResponseApi.Success -> {
                return ResponseApi.Success(responseApi.data)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }

    fun setupMoviesList(list: MyMoviesList?): List<MyMoviesListItem> {
        val movies = list
        movies?.forEach {
            it.backdropPath = it.backdropPath.getFullImageUrl()
        }
        return movies ?: listOf()
    }
}