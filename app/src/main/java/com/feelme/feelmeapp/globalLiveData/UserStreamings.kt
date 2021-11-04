package com.feelme.feelmeapp.globalLiveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object UserStreamings {
    private val userStreamings: MutableLiveData<MutableList<Int>> = MutableLiveData(mutableListOf())
    val getUserStreamings: LiveData<MutableList<Int>>
        get() = userStreamings

    fun setUserStreamings(streamIds: MutableList<Int>) {
        userStreamings.postValue(streamIds)
    }
}