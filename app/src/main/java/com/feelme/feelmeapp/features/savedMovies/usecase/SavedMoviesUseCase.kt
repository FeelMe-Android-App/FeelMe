package com.feelme.feelmeapp.features.savedMovies.usecase

import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesModel
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import com.feelme.feelmeapp.model.MyMoviesList

class SavedMoviesUseCase(private val savedMoviesRepository: SavedMoviesRepository): BaseRepository() {
    fun setupSquareMoviesList(list: MyMoviesList?): List<PagedSquareImagesModel> {
        val pagedSquareImagesModel: MutableList<PagedSquareImagesModel> = mutableListOf()
        list?.unwatched?.forEach {
            pagedSquareImagesModel.add(
                PagedSquareImagesModel(
                    it.id.toInt(),
                    it.backdropPath.getFullImageUrl()
                )
            )
        }
        return pagedSquareImagesModel.toList()
    }
}