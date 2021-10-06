package com.feelme.feelmeapp.features.savedMovies.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import kotlinx.coroutines.flow.Flow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesModel
import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesPagingSource
import com.feelme.feelmeapp.features.savedMovies.usecase.SavedMoviesUseCase
import com.feelme.feelmeapp.model.MyMoviesList
import com.feelme.feelmeapp.utils.ResponseApi

class SavedMoviesViewModel(
    private val savedMoviesRepository: SavedMoviesRepository,
    private val savedMoviesUseCase: SavedMoviesUseCase
): BaseViewModel() {
    private var mPagingDataSquare : Flow<PagingData<PagedSquareImagesModel>>? = null

    fun getUnwatchedMoviesList(): Flow<PagingData<PagedSquareImagesModel>> {
        if(mPagingDataSquare != null) return mPagingDataSquare as Flow<PagingData<PagedSquareImagesModel>>
        else
            mPagingDataSquare = Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PagedSquareImagesPagingSource { page ->
                when(
                    val response = savedMoviesRepository.getSavedMovies(page)
                ) {
                    is ResponseApi.Success -> {
                        val list = response.data as? MyMoviesList
                        savedMoviesUseCase.setupSquareMoviesList(list)
                    }
                    is ResponseApi.Error -> {
                        listOf()
                    }
                }
            } }).flow.cachedIn(viewModelScope)
        return mPagingDataSquare as Flow<PagingData<PagedSquareImagesModel>>
    }
}