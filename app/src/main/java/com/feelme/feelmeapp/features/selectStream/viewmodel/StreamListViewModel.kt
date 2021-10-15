package com.feelme.feelmeapp.features.selectStream.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.model.StreamDetails
import com.feelme.feelmeapp.modeldb.UserStreamList
import kotlinx.coroutines.launch

class StreamListViewModel(private val streamListUseCase: StreamListUseCase) : BaseViewModel() {
    private val _onSuccessStreamList: MutableLiveData<List<StreamDetails>> = MutableLiveData()
    val onSuccessStreamList: LiveData<List<StreamDetails>>
        get() = _onSuccessStreamList

    private val _onSuccessUserStreamList: MutableLiveData<List<UserStreamList>> = MutableLiveData()
    val onSuccessUserStreamList: LiveData<List<UserStreamList>>
        get() = _onSuccessUserStreamList

    fun getStreamList() {
        viewModelScope.launch {
            callApi(
                suspend { streamListUseCase.getStreamList() },
                onSuccess = {
                    _onSuccessStreamList.postValue((it as List<*>).filterIsInstance<StreamDetails>())
                }
            )
        }
    }

    fun getMyStreamListFromDb() {
        viewModelScope.launch {
            val streamList = streamListUseCase.getMyStreamListFromDb()
            _onSuccessUserStreamList.postValue(streamList)
        }
    }

    fun saveMyStreamListDb(streamList: List<UserStreamList>) {
        viewModelScope.launch {
            streamListUseCase.saveMyStreamListDb(streamList)
        }
    }
}