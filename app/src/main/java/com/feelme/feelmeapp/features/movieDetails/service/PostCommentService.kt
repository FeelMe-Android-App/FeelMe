package com.feelme.feelmeapp.features.movieDetails.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.feelme.feelmeapp.features.movieDetails.repository.MovieDetailsRepository
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.COMMENT
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.MOVIE_BACKDROP_PATH
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.MOVIE_ID
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.utils.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostCommentService(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams),
    KoinComponent {
    private val movieDetailsRepository: MovieDetailsRepository by inject()

    override suspend fun doWork(): Result {
        val movieId = inputData.getInt(MOVIE_ID, 0)
        val comment = inputData.getString(COMMENT) ?: ""
        val backdropPath = inputData.getString(MOVIE_BACKDROP_PATH) ?: ""

        return withContext(Dispatchers.IO) {
            when(val responseApi = movieDetailsRepository.postMovieComment(movieId,FeelMeMovieComment(comment, backdropPath))) {
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