package com.feelme.feelmeapp.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val homeUseCase = HomeUseCase()
    private val _onSuccessNowPlaying: MutableLiveData<List<Result>> = MutableLiveData()

    val onSuccessNowPlaying: LiveData<List<Result>>
        get() = _onSuccessNowPlaying

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getNowPlayingMovies() },
                onSuccess = {
                    _onSuccessNowPlaying.postValue(it as List<Result>)
                }
            )
        }
    }
}