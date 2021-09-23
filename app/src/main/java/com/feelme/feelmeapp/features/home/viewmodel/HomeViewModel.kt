package com.feelme.feelmeapp.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.modeldb.Genre
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
                    _onSuccessNowPlaying.postValue((it as List<*>).filterIsInstance<Result>())
                }
            )
        }
    }

    private val _onSuccessGenres: MutableLiveData<List<Genre>> = MutableLiveData()
    val onSucessGenres: LiveData<List<Genre>>
        get() = _onSuccessGenres

    fun getGenres() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getGenres() },
                onSuccess = {
                    val data = it as List<*>
                    _onSuccessGenres.postValue(data.filterIsInstance<Genre>())
                }
            )
        }
    }
}