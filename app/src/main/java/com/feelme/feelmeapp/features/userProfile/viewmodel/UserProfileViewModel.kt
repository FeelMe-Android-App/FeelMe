package com.feelme.feelmeapp.features.userProfile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.userProfile.usecase.UserProfileUseCase
import com.feelme.feelmeapp.model.MyMoviesListItem
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUserProfile
import com.feelme.feelmeapp.model.feelmeapi.LastComments
import com.feelme.feelmeapp.model.feelmeapi.LastWatchedMovies
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userProfileUseCase: UserProfileUseCase): BaseViewModel() {
    private val _onSuccessUserProfile: MutableLiveData<FeelMeUserProfile> = MutableLiveData()
    val onSuccessUserProfile: LiveData<FeelMeUserProfile>
        get() = _onSuccessUserProfile

    private val _onSuccessLastWatchedMovies: MutableLiveData<List<LastWatchedMovies>> = MutableLiveData()
    val onSuccessLastWatchedMovies: LiveData<List<LastWatchedMovies>>
        get() = _onSuccessLastWatchedMovies

    private val _onSuccessLastComments: MutableLiveData<List<LastComments>> = MutableLiveData()
    val onSuccessLastComments: LiveData<List<LastComments>>
        get() = _onSuccessLastComments

    fun getUserProfile(uid: String) {
        viewModelScope.launch {
            callApi(
                suspend { userProfileUseCase.getUserProfile(uid) },
                onSuccess = { UserProfile ->
                    val lastWatched = (UserProfile as FeelMeUserProfile).userprofile.lastwatched?.map {
                        it.backdropPath = it.backdropPath.getFullImageUrl()
                        it
                    }
                    val lastComments = (UserProfile as FeelMeUserProfile).userprofile.lastcomments?.map {
                        it.backdropPath = it.backdropPath.getFullImageUrl()
                        it.photoUrl = UserProfile.userprofile.photoUrl
                        it
                    }
                    _onSuccessUserProfile.postValue(UserProfile)

                    if(!lastWatched.isNullOrEmpty()) _onSuccessLastWatchedMovies.postValue((lastWatched as List<*>).filterIsInstance<LastWatchedMovies>())
                    if(!lastComments.isNullOrEmpty()) _onSuccessLastComments.postValue((lastComments as List<*>).filterIsInstance<LastComments>())
                }
            )
        }
    }
}