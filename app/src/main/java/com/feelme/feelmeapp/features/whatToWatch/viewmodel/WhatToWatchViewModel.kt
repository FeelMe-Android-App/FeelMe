package com.feelme.feelmeapp.features.whatToWatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.whatToWatch.usecase.WhatToWatchUseCase
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.launch

class WhatToWatchViewModel: BaseViewModel() {
    private val whatToWatchUseCase = WhatToWatchUseCase()
    private val _onSuccessWhatToWatch = MutableLiveData<List<Result>>()
    val onSuccessWhatToWatch: LiveData<List<Result>>
        get() = _onSuccessWhatToWatch

    fun getDiscoverMovies(providers: String = "", genres: String = "") {
        viewModelScope.launch {
            callApi(
                suspend { whatToWatchUseCase.getDiscoverMovies(providers, genres) },
                onSuccess = {
                    _onSuccessWhatToWatch.postValue(it as List<Result>)
                }
            )
        }
    }
}