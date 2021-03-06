package com.feelme.feelmeapp.globalLiveData

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.Profile
import com.feelme.feelmeapp.model.Follow
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.internal.IdTokenListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.internal.InternalTokenResult
import com.google.firebase.ktx.Firebase


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
        val streaming: MutableList<Int> = mutableListOf(),
        val uid: String?,
        val logged: Boolean = false,
    )

    private val currentLiveData: MutableLiveData<User?> = MutableLiveData()
    private lateinit var auth: FirebaseAuth

    val currentUser: LiveData<User?>
        get() = currentLiveData

    init {
        val auth = Firebase.auth
        val user = auth.currentUser

        patchInitialValue()

        FirebaseAuth.getInstance().addIdTokenListener(object : IdTokenListener {
            override fun onIdTokenChanged(p0: InternalTokenResult) {
                p0.token?.let { token ->
                    user?.let { patchUserValue(user, token) }
                }
            }
        })
    }

    fun patchInitialValue() {
        Firebase.auth.currentUser?.let { User ->
            User.getIdToken(true)
                .addOnCompleteListener(object : OnCompleteListener<GetTokenResult?> {
                    override fun onComplete(task: Task<GetTokenResult?>) {
                        if (task.isSuccessful()) {
                            task.result?.let {
                                it.token?.let { patchUserValue(User, it) }
                            }
                        }
                    }
                })
        }
    }

    fun logOut() {
        resetUserValue()
    }

    private fun patchUserValue(user: FirebaseUser, token: String) {
        currentLiveData.postValue(User(
            providerId = user.providerId,
            displayName = user.displayName ?: "",
            email = user.email ?: "",
            photoUrl = Profile.getCurrentProfile().getProfilePictureUri(100, 100),
            photoUrlThumb = Profile.getCurrentProfile().getProfilePictureUri(40, 40),
            token = token,
            uid = FirebaseAuth.getInstance().currentUser?.uid.toString(),
            logged = true
        ))
    }

    private fun resetUserValue() {
        currentLiveData.postValue(User(
            providerId = null,
            displayName = null,
            email = null,
            photoUrl = null,
            photoUrlThumb = null,
            token = null,
            uid = null,
            logged = false
        ))
    }
}