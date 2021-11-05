package com.feelme.feelmeapp.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import com.feelme.feelmeapp.globalLiveData.UserStreamings
import com.feelme.feelmeapp.model.Genre
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase): BaseViewModel() {
    private val _onSuccessNowPlaying: MutableLiveData<List<Result>> = MutableLiveData()
    val onSuccessNowPlaying: LiveData<List<Result>>
        get() = _onSuccessNowPlaying

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getNowPlayingMovies(UserStreamings.getUserStreamingsServices()) },
                onSuccess = {
                    _onSuccessNowPlaying.postValue((it as List<*>).filterIsInstance<Result>())
                }
            )
        }
    }

    private val _onSuccessLastRelease: MutableLiveData<List<Result>> = MutableLiveData()
    val onSuccessLastRelease: LiveData<List<Result>>
        get() = _onSuccessLastRelease

    fun getLastRelease() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getLastRelease(UserStreamings.getUserStreamingsServices()) },
                onSuccess = {
                    _onSuccessLastRelease.postValue((it as List<*>).filterIsInstance<Result>())
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