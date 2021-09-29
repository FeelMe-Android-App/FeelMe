package com.feelme.feelmeapp.features.genre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.genre.paging.GenrePagingSource
import com.feelme.feelmeapp.features.genre.repository.GenreRepository
import com.feelme.feelmeapp.features.genre.usecase.GenreUseCase
import com.feelme.feelmeapp.features.genre.view.GenreActivity.Companion.PAGE_SIZE
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GenreViewModel(private val genreUseCase: GenreUseCase, private val genreRepository: GenreRepository): BaseViewModel() {
    var mPagingData: Flow<PagingData<Result>>? = null
    private val _onSuccessMoviesByGenre: MutableLiveData<List<Result>> = MutableLiveData()
    val onSuccessMoviesByGenre: LiveData<List<Result>>
        get() = _onSuccessMoviesByGenre

    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Result>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<Result>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { GenrePagingSource(genreRepository, genreUseCase, genreId) }
            ).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<Result>>
    }
}