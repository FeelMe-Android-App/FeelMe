package com.feelme.feelmeapp.features.dialog.usecase

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.feelme.feelmeapp.features.dialog.repository.DialogRepository
import com.feelme.feelmeapp.features.dialog.service.SaveStreamListService
import com.feelme.feelmeapp.features.movieDetails.service.SaveUnwatchedMovieService
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