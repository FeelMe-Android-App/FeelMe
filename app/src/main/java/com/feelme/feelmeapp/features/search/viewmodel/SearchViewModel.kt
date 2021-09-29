package com.feelme.feelmeapp.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.search.usecase.SearchUseCase
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.launch

class SearchViewModel(private val searchUseCase: SearchUseCase): BaseViewModel() {
    private val _onSuccessSearch: MutableLiveData<List<Result>> = MutableLiveData()
    val onSuccessSearch: LiveData<List<Result>>
        get() = _onSuccessSearch

    fun getSearch(query: String) {
        viewModelScope.launch {
            callApi(
                    suspend { searchUseCase.getSearch(query) },
                    onSuccess = {
                        _onSuccessSearch.postValue((it as List<*>).filterIsInstance<Result>())
                    }
            )
        }
    }
}