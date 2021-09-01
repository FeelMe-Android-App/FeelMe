package com.feelme.feelmeapp.features.movieDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel: BaseViewModel() {
    private val movieDetailsUseCase = MovieDetailsUseCase()
    private val _onSuccessMovieDetails: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieDetails: LiveData<Movie>
        get() = _onSuccessMovieDetails

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            callApi(
                suspend { movieDetailsUseCase.getMovieById(id) },
                onSuccess = {
                    _onSuccessMovieDetails.postValue(it as Movie)
                }
            )
        }
    }
}