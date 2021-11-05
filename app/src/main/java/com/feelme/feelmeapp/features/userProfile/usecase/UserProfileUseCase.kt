package com.feelme.feelmeapp.features.userProfile.usecase

import com.feelme.feelmeapp.features.userProfile.repository.UserProfileRepository
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUserProfile
import com.feelme.feelmeapp.utils.ResponseApi

class UserProfileUseCase(private val userProfileRepository: UserProfileRepository) {
    suspend fun getUserProfile(uid: String): ResponseApi {
        when(val responseApi = userProfileRepository.getUserProfile(uid)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? FeelMeUserProfile
                return ResponseApi.Success(data)
            }
            else -> return responseApi
        }
    }
}