package com.feelme.feelmeapp.features.dialog.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feelme.feelmeapp.features.dialog.repository.DialogRepository
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SaveStreamListService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val dialogRepository: DialogRepository by inject()

    override suspend fun doWork(): Result {
        val streamList = dialogRepository.getUserStreaming().map {
            it.userStream.streamId
        }

        return withContext(Dispatchers.IO) {
            when(val responseApi = dialogRepository.saveUserStreamings(streamList)) {
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