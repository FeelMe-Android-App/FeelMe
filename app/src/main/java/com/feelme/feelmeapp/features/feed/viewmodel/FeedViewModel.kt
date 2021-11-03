package com.feelme.feelmeapp.features.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.feelme.feelmeapp.adapters.PagingMovieComments.PagedMovieCommentsModel
import com.feelme.feelmeapp.adapters.PagingMovieComments.PagedMovieCommentsPagingSource
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.feed.repository.FeedRepository
import com.feelme.feelmeapp.features.feed.usecase.FeedUseCase
import com.feelme.feelmeapp.model.feelmeapi.FeelMeComments
import com.feelme.feelmeapp.model.feelmeapi.FeelMeFollow
import com.feelme.feelmeapp.model.feelmeapi.FriendMovieItem
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class FeedViewModel(private val feedUseCase: FeedUseCase, private val feedRepository: FeedRepository): BaseViewModel() {
    private val _onSuccessFriendsStatus: MutableLiveData<List<FriendMovieItem>> = MutableLiveData()
    val onSuccessFriendsStatus: LiveData<List<FriendMovieItem>>
        get() = _onSuccessFriendsStatus

    private val _noStatus: MutableLiveData<Boolean> = MutableLiveData()
    val noStatus: LiveData<Boolean>
        get() = _noStatus

    private val _onSuccessFollow: MutableLiveData<FeelMeFollow> = MutableLiveData()
    val onSuccessFollow: LiveData<FeelMeFollow>
        get() = _onSuccessFollow

    var mPagingData: Flow<PagingData<PagedMovieCommentsModel>>? = null

    fun getFriendsStatus() {
        viewModelScope.launch {
            callApi(
                suspend { feedUseCase.getFriendsStatus() },
                onSuccess = {
                    val list = it as List<*>
                    if(list.isNullOrEmpty()) _noStatus.postValue(true)
                    else {
                        val data = list.filterIsInstance<FriendMovieItem>()
                        _onSuccessFriendsStatus.postValue(data)
                    }
                },
                onError = {

                }
            )
        }
    }

    fun getFriendsComments(): Flow<PagingData<PagedMovieCommentsModel>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<PagedMovieCommentsModel>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { PagedMovieCommentsPagingSource { page ->
                    when(val responseApi = feedRepository.getFriendsComments(page)) {
                        is ResponseApi.Success -> {
                            val list = responseApi.data as? FeelMeComments
                            feedUseCase.setupMovieCommentsList(list)
                        }
                        is ResponseApi.Error -> {
                            listOf()
                        }
                    }
                }}
            ).flow.cachedIn(viewModelScope)
        return mPagingData as Flow<PagingData<PagedMovieCommentsModel>>
    }

    fun getUserFollow() {
        viewModelScope.launch {
            callApi(
                suspend { feedUseCase.getUserFollow() },
                onSuccess = {
                    val data = it as FeelMeFollow
                    _onSuccessFollow.postValue(data)
                }
            )
        }
    }
}