package com.feelme.feelmeapp.features.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.profile.usecase.ProfileUseCase
import com.feelme.feelmeapp.model.UserProfileData
import com.feelme.feelmeapp.model.feelmeapi.FeelMeFollow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase): BaseViewModel() {
    private val _onSuccessProfile: MutableLiveData<UserProfileData> = MutableLiveData()
    val onSuccessProfile: LiveData<UserProfileData>
        get() = _onSuccessProfile

    fun getMyProfile() {
        viewModelScope.launch {
            callApi(
                suspend { profileUseCase.getMyProfile() },
                onSuccess = {
                    val data = it as UserProfileData
                    _onSuccessProfile.postValue(data)
                }
            )
        }
    }
}