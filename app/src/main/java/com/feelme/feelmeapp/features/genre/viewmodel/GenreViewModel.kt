package com.feelme.feelmeapp.features.genre.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridModel
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.features.genre.usecase.GenreUseCase
import com.feelme.feelmeapp.features.genre.view.GenreActivity.Companion.PAGE_SIZE
import com.feelme.feelmeapp.model.DiscoverMovies
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.flow.Flow

class GenreViewModel(private val genreUseCase: GenreUseCase, private val genreRepository: GenreRepository): BaseViewModel() {
    var mPagingData: Flow<PagingData<PagedMovieGridModel>>? = null

    fun getMoviesByGenre(providers: String, genreId: Int): Flow<PagingData<PagedMovieGridModel>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<PagedMovieGridModel>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PagedMovieGridPagingSource { page ->
                when(
                    val response = genreRepository.getMoviesByGenre(providers, genreId, page)
                ) {
                    is ResponseApi.Success -> {
                        val list = response.data as? DiscoverMovies
                        genreUseCase.setupGridMoviesList(list)
                    }
                    is ResponseApi.Error -> {
                        listOf()
                    }
                }
            }}).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<PagedMovieGridModel>>
    }
}