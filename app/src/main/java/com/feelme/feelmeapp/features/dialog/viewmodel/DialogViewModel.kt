package com.feelme.feelmeapp.features.dialog.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.dialog.service.SaveStreamListService
import com.feelme.feelmeapp.features.dialog.usecase.DialogUseCase
import com.feelme.feelmeapp.model.FeelMeNewUserPost
import kotlinx.coroutines.launch

class DialogViewModel(private val dialogUseCase: DialogUseCase, private val context: Context): BaseViewModel() {
    private val _onSuccessUserProfile: MutableLiveData<String> = MutableLiveData()
    val onSuccessUserProfile: LiveData<String>
        get() = _onSuccessUserProfile

    fun saveUserProfile(userData: FeelMeNewUserPost) {
        viewModelScope.launch {
            callApi(
                suspend { dialogUseCase.saveUserProfile(userData) },
                onSuccess = {
                    _onSuccessUserProfile.postValue(it.toString())
                },
                onError = {

                }
            )
        }
    }

    fun saveUserStreamings() {
        val saveStreamList: WorkRequest = OneTimeWorkRequestBuilder<SaveStreamListService>().build()
        WorkManager.getInstance(context).enqueue(saveStreamList)
    }
}