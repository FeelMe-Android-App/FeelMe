package com.feelme.feelmeapp.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridModel
import com.feelme.feelmeapp.adapters.PagingMovieGridAdapter.PagedMovieGridPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.genre.view.GenreActivity.Companion.PAGE_SIZE
import com.feelme.feelmeapp.features.search.repository.SearchRepository
import com.feelme.feelmeapp.features.search.usecase.SearchUseCase
import com.feelme.feelmeapp.model.Search
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val searchUseCase: SearchUseCase, private val searchRepository: SearchRepository): BaseViewModel() {
    var mPagingData: Flow<PagingData<PagedMovieGridModel>>? = null
    private val _onSuccessSearch: MutableLiveData<String> = MutableLiveData()
    val onSuccessSearch: LiveData<String>
        get() = _onSuccessSearch

    fun getSearchMovies(query: String, initialLoad: Boolean = false): Flow<PagingData<PagedMovieGridModel>> {
        if(mPagingData != null && !initialLoad) return mPagingData as Flow<PagingData<PagedMovieGridModel>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PagedMovieGridPagingSource { page ->
                when(
                    val response = searchRepository.getSearch(query, page)
                ) {
                    is ResponseApi.Success -> {
                        if(page == 1) _onSuccessSearch.postValue("loaded")
                        val list = response.data as? Search
                        searchUseCase.setupGridMovieList(list)
                    }
                    is ResponseApi.Error -> {
                        listOf()
                    }
                }
            }}).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<PagedMovieGridModel>>
    }

    fun recyclerViewReloaded() {
        _onSuccessSearch.postValue("refreshed")
    }
}