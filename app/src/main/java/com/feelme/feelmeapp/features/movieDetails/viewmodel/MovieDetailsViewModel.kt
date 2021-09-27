package com.feelme.feelmeapp.features.movieDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.model.Flatrate
import com.feelme.feelmeapp.model.Result
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieDetailsUseCase: MovieDetailsUseCase): BaseViewModel() {
    private val _onSuccessMovieDetails: MutableLiveData<Result> = MutableLiveData()
    val onSuccessMovieDetails: LiveData<Result>
        get() = _onSuccessMovieDetails

    private val _onSuccessMovieStreaming: MutableLiveData<List<Flatrate>> = MutableLiveData()
    val onSuccessMovieStreaming: LiveData<List<Flatrate>>
        get() = _onSuccessMovieStreaming

    fun getMovieDetailsScreen(movieId: Int) {
        viewModelScope.let {
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieById(movieId) },
                    onSuccess = {
                        _onSuccessMovieDetails.postValue(it as Result)
                    }
                )
            }
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieStreamings(movieId) },
                    onSuccess = {
                        _onSuccessMovieStreaming.postValue((it as List<*>).filterIsInstance<Flatrate>())
                    }
                )
            }
        }
    }
}