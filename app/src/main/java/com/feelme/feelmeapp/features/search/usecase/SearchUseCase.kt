package com.feelme.feelmeapp.features.search.usecase

import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.search.repository.SearchRepository
import com.feelme.feelmeapp.model.Search

class SearchUseCase(private val searchRepository: SearchRepository) {
    fun setupGridMovieList(list: Search?): List<PagedMovieGridModel> {
        val pagedMovieGridMovel: MutableList<PagedMovieGridModel> = mutableListOf()
        list?.results?.forEach {
            pagedMovieGridMovel.add(
                PagedMovieGridModel(
                    it.id,
                    it.posterPath?.getFullImageUrl(),
                    it.title
                )
            )
        }
        return pagedMovieGridMovel
    }
}