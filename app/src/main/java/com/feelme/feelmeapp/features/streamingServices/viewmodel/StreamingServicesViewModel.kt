package com.feelme.feelmeapp.features.streamingServices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.streamingServices.usecase.StreamingServicesUseCase
import com.feelme.feelmeapp.modeldb.UserStreamListWithStream
import kotlinx.coroutines.launch

class StreamingServicesViewModel(private val streamingServicesUseCase: StreamingServicesUseCase): BaseViewModel() {
    private val _onSuccessStreamingServices: MutableLiveData<List<UserStreamListWithStream>> = MutableLiveData()
    val onSuccessStreamingServices: LiveData<List<UserStreamListWithStream>>
        get() = _onSuccessStreamingServices
    private val _noStreamingServices: MutableLiveData<Boolean> = MutableLiveData()
    val noStreamingServices: LiveData<Boolean>
        get() = _noStreamingServices

    fun getMyStreamingServices() {
        viewModelScope.launch {
            val streamingList = streamingServicesUseCase.getMyStreamingServices()
            if(!streamingList.isNullOrEmpty()) {
                _onSuccessStreamingServices.postValue(streamingList)
            } else _noStreamingServices.postValue(true)
        }
    }
}