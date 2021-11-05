package com.feelme.feelmeapp.features.profile.usecase

import com.feelme.feelmeapp.features.profile.repository.ProfileRepository
import com.feelme.feelmeapp.model.UserProfileData
import com.feelme.feelmeapp.utils.ResponseApi

class ProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend fun getMyProfile(): ResponseApi {
        when(val responseApi = profileRepository.getMyProfile()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as UserProfileData
                return ResponseApi.Success(data)
            }
            else -> return responseApi
        }
    }
}