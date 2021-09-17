package com.feelme.feelmeapp.features.selectStream.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.model.StreamDetails
import kotlinx.coroutines.launch

class StreamListViewModel: BaseViewModel() {
    private val streamList = StreamListUseCase()
    private val _onSuccessStreamList: MutableLiveData<List<StreamDetails>> = MutableLiveData()
    val onSuccessStreamList: LiveData<List<StreamDetails>>
        get() = _onSuccessStreamList

    fun getStreamList() {
        viewModelScope.launch {
            callApi(
                suspend { streamList.getStreamList() },
                onSuccess = {
                    _onSuccessStreamList.postValue((it as List<*>).filterIsInstance<StreamDetails>())
                }
            )
        }
    }
}