package com.feelme.feelmeapp.features.userProfile.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feelme.feelmeapp.features.searchFriend.view.SearchFriendFragment
import com.feelme.feelmeapp.features.userProfile.repository.UserProfileRepository
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowUserService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val userProfileRepository: UserProfileRepository by inject()

    override suspend fun doWork(): Result {
        val uid = inputData.getString(SearchFriendFragment.USER_ID) ?: ""

        return withContext(Dispatchers.IO) {
            when(val responseApi = userProfileRepository.followUser(uid)) {
                is ResponseApi.Success -> {
                    Result.success()
                }
                is ResponseApi.Error -> {
                    Result.failure()
                }
            }
        }
    }
}