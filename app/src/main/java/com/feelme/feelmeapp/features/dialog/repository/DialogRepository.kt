package com.feelme.feelmeapp.features.dialog.repository

import android.content.Context
import androidx.work.ListenableWorker
import com.feelme.feelmeapp.api.FeelMeApiService
import com.feelme.feelmeapp.base.BaseRepository
import com.feelme.feelmeapp.database.FeelMeDatabase
import com.feelme.feelmeapp.model.FeelMeNewUserPost
import com.feelme.feelmeapp.modeldb.UserStreamListWithStream
import com.feelme.feelmeapp.utils.ResponseApi

class DialogRepository(private val context: Context) : BaseRepository() {
    suspend fun saveUserProfile(userData: FeelMeNewUserPost): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.saveMyProfile(userData)
        }
    }

    suspend fun saveUserStreamings(
        streamList: List<Int>
    ): ResponseApi {
        return safeApiCall {
            FeelMeApiService.feelMeApiService.saveStream(streamList)
        }
    }

    suspend fun getUserStreaming(): List<UserStreamListWithStream> {
        return FeelMeDatabase.getDatabase(context).userStream().getUserStream()
    }
}