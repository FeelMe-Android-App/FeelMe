package com.feelme.feelmeapp.features.genre.usecase

import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.model.DiscoverMovies

class GenreUseCase {
    fun setupGridMoviesList(list: DiscoverMovies?): List<PagedMovieGridModel> {
        val pagedMovieGridModel: MutableList<PagedMovieGridModel> = mutableListOf()
        list?.results?.forEach {
            pagedMovieGridModel.add(
                PagedMovieGridModel(
                    it.id,
                    it.posterPath?.getFullImageUrl() ?: "",
                    it.title
                )
            )
        }
        return pagedMovieGridModel
    }
}