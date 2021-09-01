package com.feelme.feelmeapp.features.selectStream.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.selectStream.usecase.StreamListUseCase
import com.feelme.feelmeapp.model.StreamDetais
import kotlinx.coroutines.launch

class StreamListViewModel: BaseViewModel() {
    private val streamList = StreamListUseCase()
    private val _onSuccessStreamList: MutableLiveData<List<StreamDetais>> = MutableLiveData()
    val onSuccessStreamList: LiveData<List<StreamDetais>>
        get() = _onSuccessStreamList

    fun getStreamList() {
        viewModelScope.launch {
            callApi(
                suspend { streamList.getStreamList() },
                onSuccess = {
                    _onSuccessStreamList.postValue(it as List<StreamDetais>)
                }
            )
        }
    }
}