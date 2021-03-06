package com.feelme.feelmeapp.features.dialog.usecase

import com.feelme.feelmeapp.features.dialog.repository.DialogRepository
import com.feelme.feelmeapp.model.FeelMeNewUserPost
import com.feelme.feelmeapp.utils.ResponseApi

class DialogUseCase(private val dialogRepository: DialogRepository) {
    suspend fun saveUserProfile(userData: FeelMeNewUserPost): ResponseApi {
        when (val responseApi = dialogRepository.saveUserProfile(userData)) {
            is ResponseApi.Success -> {
                return ResponseApi.Success("Salvo com sucesso")
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }
}