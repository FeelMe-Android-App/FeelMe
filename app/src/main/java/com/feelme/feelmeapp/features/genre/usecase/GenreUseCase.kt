package com.feelme.feelmeapp.features.genre.usecase

import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.toMovieDb
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.utils.ResponseApi

class GenreUseCase() {
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