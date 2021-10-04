package com.feelme.feelmeapp.features.savedMovies.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.savedMovies.repository.SavedMoviesRepository
import com.feelme.feelmeapp.features.savedMovies.usecase.SavedMoviesUseCase
import com.feelme.feelmeapp.model.MyMoviesListItem
import kotlinx.coroutines.flow.Flow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.feelme.feelmeapp.features.savedMovies.paging.SavedMoviesPagingSource

class SavedMoviesViewModel(
    private val savedMoviesUseCase: SavedMoviesUseCase,
    private val savedMoviesRepository: SavedMoviesRepository
): BaseViewModel() {
    private var mPagingData : Flow<PagingData<MyMoviesListItem>>? = null

    fun getList(): Flow<PagingData<MyMoviesListItem>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<MyMoviesListItem>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SavedMoviesPagingSource(savedMoviesRepository, savedMoviesUseCase) }).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<MyMoviesListItem>>
    }
}