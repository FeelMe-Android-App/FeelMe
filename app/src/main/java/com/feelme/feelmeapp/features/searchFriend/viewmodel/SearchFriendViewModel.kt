package com.feelme.feelmeapp.features.searchFriend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsModel
import com.feelme.feelmeapp.adapters.PagingFriendsAdapter.PagedFriendsPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.genre.view.GenreActivity.Companion.PAGE_SIZE
import com.feelme.feelmeapp.features.searchFriend.repository.SearchFriendRepository
import com.feelme.feelmeapp.features.searchFriend.usecase.SearchFriendUseCase
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUsersSearch
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.flow.Flow

class SearchFriendViewModel(private val searchFriendUseCase: SearchFriendUseCase, private val searchFriendRepository: SearchFriendRepository) : BaseViewModel()  {
    var mPagingData: Flow<PagingData<PagedFriendsModel>>? = null
    private val _onSuccessFriendSearch: MutableLiveData<String> = MutableLiveData()
    val onSuccessFriendSearch: LiveData<String>
        get() = _onSuccessFriendSearch

    fun getFriendSearch(query: String, initialLoad: Boolean = false): Flow<PagingData<PagedFriendsModel>> {
        if(mPagingData != null && !initialLoad) return mPagingData as Flow<PagingData<PagedFriendsModel>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { PagedFriendsPagingSource { page ->
                    when(
                        val response = searchFriendRepository.getFriendSearch(query, page)
                    ) {
                        is ResponseApi.Success -> {
                            if(page == 1) _onSuccessFriendSearch.postValue("loaded")
                            val list = response.data as? FeelMeUsersSearch
                            searchFriendUseCase.setupFriendsList(list)
                        }
                        is ResponseApi.Error -> {
                            listOf()
                        }
                    }
                }}).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<PagedFriendsModel>>
    }

    fun recyclerViewReloaded() {
        _onSuccessFriendSearch.postValue("refreshed")
    }
}