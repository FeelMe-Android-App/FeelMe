package com.feelme.feelmeapp.firebase

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.Profile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.GetTokenResult

import androidx.annotation.NonNull
import com.feelme.feelmeapp.model.Follow
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


object UserProfile {
    class User(
        var providerId: String? = null,
        var displayName: String? = null,
        var status: String? = null,
        var email: String? = null,
        var photoUrl: Uri? = null,
        var photoUrlThumb: Uri? = null,
        val token: String? = null,
        val follow: MutableList<Follow> = mutableListOf(),
        val followed: MutableList<Follow> = mutableListOf(),
        val streaming: MutableList<Int> = mutableListOf()
    )

    private val currentLiveData: MutableLiveData<User?> = MutableLiveData()

    val currentUser: LiveData<User?>
        get() = currentLiveData

    init {
        updateProfile()
    }

    fun updateProfile() {
        val user = Firebase.auth.currentUser
        if(user != null) {
            user.getIdToken(false)
                .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                    override fun onComplete(task: Task<GetTokenResult?>) {
                        if (task.isSuccessful()) {
                            task.result?.let {
                                currentLiveData.postValue(User(
                                    providerId = user.providerId,
                                    displayName = user.displayName ?: "",
                                    email = user.email ?: "",
                                    photoUrl = Profile.getCurrentProfile().getProfilePictureUri(100, 100),
                                    photoUrlThumb = Profile.getCurrentProfile().getProfilePictureUri(40, 40),
                                    token = it.token
                                ))
                            }
                        }
                    }
                })
        } else {
            currentLiveData.postValue(null)
        }
    }
}