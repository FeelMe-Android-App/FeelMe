package com.feelme.feelmeapp.features.watchedMovies.usecase

import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesModel
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.model.MyMoviesList

class WatchedMoviesUseCase: BaseRepository() {
    fun setupSquareMoviesList(list: MyMoviesList?): List<PagedSquareImagesModel> {
        val pagedSquareImagesModel: MutableList<PagedSquareImagesModel> = mutableListOf()
        list?.watched?.forEach {
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