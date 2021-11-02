package com.feelme.feelmeapp.globalLiveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object UserMoviesList {
    private val _WatchedMovieList: MutableLiveData<Boolean> = MutableLiveData()
    val hasWatchedMovieListChanged: LiveData<Boolean>
        get() = _WatchedMovieList
    private val _UnwatchedMovieList: MutableLiveData<Boolean> = MutableLiveData()
    val hasUnwatchedMovieListChanged: LiveData<Boolean>
        get() = _UnwatchedMovieList

    fun emitWatchedMovieHasChanged(changed: Boolean) {
        this._WatchedMovieList.postValue(changed)
    }

    fun emitUnwatchedMovieHasChanged(changed: Boolean) {
        this._UnwatchedMovieList.postValue(changed)
    }
}