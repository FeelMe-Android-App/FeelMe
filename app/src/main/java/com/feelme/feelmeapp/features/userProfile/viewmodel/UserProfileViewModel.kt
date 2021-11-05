package com.feelme.feelmeapp.features.userProfile.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment
import com.feelme.feelmeapp.features.userProfile.service.FollowUserService
import com.feelme.feelmeapp.features.userProfile.service.UnfollowUserService
import com.feelme.feelmeapp.features.userProfile.usecase.UserProfileUseCase
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUserProfile
import com.feelme.feelmeapp.model.feelmeapi.LastComments
import com.feelme.feelmeapp.model.feelmeapi.LastWatchedMovies
import kotlinx.coroutines.launch

class UserProfileViewModel(private val context: Context, private val userProfileUseCase: UserProfileUseCase): BaseViewModel() {
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
                    val lastWatched = (UserProfile as FeelMeUserProfile).userprofile.lastwatched.map {
                        it.backdropPath = it.backdropPath.getFullImageUrl()
                        it
                    }
                    val lastComments = UserProfile.userprofile.lastcomments?.map {
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

    fun followUser(uid: String) {
        val data = workDataOf(SearchFriendFragment.USER_ID to uid)
        val followUserRequest: WorkRequest = OneTimeWorkRequestBuilder<FollowUserService>().setInputData(data).build()
        WorkManager.getInstance(context).enqueue(followUserRequest)
    }

    fun unfollowUser(uid: String) {
        val data = workDataOf(SearchFriendFragment.USER_ID to uid)
        val unfollowUserRequest: WorkRequest = OneTimeWorkRequestBuilder<UnfollowUserService>().setInputData(data).build()
        WorkManager.getInstance(context).enqueue(unfollowUserRequest)
    }
}