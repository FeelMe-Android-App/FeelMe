package com.feelme.feelmeapp.features.watchedMovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesModel
import com.feelme.feelmeapp.adapters.PagingSquareAdapter.PagedSquareImagesPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.watchedMovies.repository.WatchedMoviesRepository
import com.feelme.feelmeapp.features.watchedMovies.usecase.WatchedMoviesUseCase
import com.feelme.feelmeapp.model.MyMoviesList
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.flow.Flow

class WatchedMoviesModel(
    private val watchedMoviesRepository: WatchedMoviesRepository,
    private val watchedMoviesUseCase: WatchedMoviesUseCase
): BaseViewModel() {
    private var mPagingData: Flow<PagingData<PagedSquareImagesModel>>? = null
    private val _noWatchedMovies: MutableLiveData<Boolean> = MutableLiveData()
    val noWatchedMovies: LiveData<Boolean>
        get() = _noWatchedMovies

    fun getWatchedMoviesList(): Flow<PagingData<PagedSquareImagesModel>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<PagedSquareImagesModel>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { PagedSquareImagesPagingSource { page ->
                    when(
                        val response = watchedMoviesRepository.getWatchedMovies(page)
                    ) {
                        is ResponseApi.Success -> {
                            val list = response.data as? MyMoviesList
                            if(list == null) _noWatchedMovies.postValue(true)
                            watchedMoviesUseCase.setupSquareMoviesList(list)
                        }
                        is ResponseApi.Error -> {
                            listOf()
                        }
                    }
                } }).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<PagedSquareImagesModel>>
    }
}