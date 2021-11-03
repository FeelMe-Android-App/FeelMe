package com.feelme.feelmeapp.features.movieDetails.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteCommentService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val movieDetailsRepository: MovieDetailsRepository by inject()

    override suspend fun doWork(): Result {
        val commentId = inputData.getString(MovieDetailsActivity.COMMENT_ID) ?: ""

        return withContext(Dispatchers.IO) {
            when (val responseApi = movieDetailsRepository.deleteComment(
                commentId
            )) {
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