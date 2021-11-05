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

class RemoveMovieService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val movieDetailsRepository: MovieDetailsRepository by inject()

    override suspend fun doWork(): Result {
        val movieId = inputData.getInt(MovieDetailsActivity.MOVIE_ID, 0)

        return withContext(Dispatchers.IO) {
            when(val responseApi = movieDetailsRepository.removeMovie(movieId)) {
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