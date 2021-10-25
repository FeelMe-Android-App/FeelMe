package com.feelme.feelmeapp.features.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.feed.usecase.FeedUseCase
import com.feelme.feelmeapp.model.feelmeapi.FriendMovieItem
import kotlinx.coroutines.launch

class FeedViewModel(private val feedUseCase: FeedUseCase): BaseViewModel() {
    private val _onSuccessFriendsStatus: MutableLiveData<List<FriendMovieItem>> = MutableLiveData()
    val onSuccessFriendsStatus: LiveData<List<FriendMovieItem>>
        get() = _onSuccessFriendsStatus

    private val _noStatus: MutableLiveData<Boolean> = MutableLiveData()
    val noStatus: LiveData<Boolean>
        get() = _noStatus

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
}