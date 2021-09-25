package com.feelme.feelmeapp.features.movieDetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.model.Flatrate
import com.feelme.feelmeapp.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel: BaseViewModel() {

    private val movieDetailsUseCase = MovieDetailsUseCase()
    private val _onSuccessMovieDetails: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieDetails: LiveData<Movie>
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
                        _onSuccessMovieDetails.postValue(it as Movie)
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