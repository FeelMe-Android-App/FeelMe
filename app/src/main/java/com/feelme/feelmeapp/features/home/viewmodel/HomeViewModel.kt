package com.feelme.feelmeapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.features.home.usecase.HomeUseCase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val homeUseCase = HomeUseCase()

    fun getNowPlayingMovies(){

        viewModelScope.launch {
            homeUseCase.getNowPlayingMovies()
        }



    }


}