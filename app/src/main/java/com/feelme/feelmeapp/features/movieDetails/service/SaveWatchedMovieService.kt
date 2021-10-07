package com.feelme.feelmeapp.features.movieDetails.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SaveWatchedMovieService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val movieDetailsRepository: MovieDetailsRepository by inject()

    override suspend fun doWork(): Result {
        val movieId = inputData.getInt(MovieDetailsActivity.MOVIE_ID, 0)
        val backdropPath = inputData.getString(MovieDetailsActivity.MOVIE_BACKDROP_PATH) ?: ""
        val title = inputData.getString(MovieDetailsActivity.MOVIE_TITLE) ?: ""

        return withContext(Dispatchers.IO) {
            when(val responseApi = movieDetailsRepository.saveWatchedMovie(movieId, FeelMeMovie(backdropPath, title))) {
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