package com.feelme.feelmeapp.features.movieDetails.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.feelme.feelmeapp.base.BaseViewModel
import com.feelme.feelmeapp.features.movieDetails.service.PostCommentService
import com.feelme.feelmeapp.features.movieDetails.service.RemoveMovieService
import com.feelme.feelmeapp.features.movieDetails.service.SaveUnwatchedMovieService
import com.feelme.feelmeapp.features.movieDetails.service.SaveWatchedMovieService
import com.feelme.feelmeapp.features.movieDetails.usecase.Comment
import com.feelme.feelmeapp.features.movieDetails.usecase.MovieDetailsUseCase
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.COMMENT
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.MOVIE_BACKDROP_PATH
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.MOVIE_ID
import com.feelme.feelmeapp.features.movieDetails.view.MovieDetailsActivity.Companion.MOVIE_TITLE
import com.feelme.feelmeapp.model.Flatrate
import com.feelme.feelmeapp.model.Result
import com.feelme.feelmeapp.model.feelmeapi.FeelMeComments
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieComment
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieStatus
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieDetailsUseCase: MovieDetailsUseCase, private val context: Context): BaseViewModel() {
    private val _onSuccessMovieDetails: MutableLiveData<Result> = MutableLiveData()
    val onSuccessMovieDetails: LiveData<Result>
        get() = _onSuccessMovieDetails

    private val _onSuccessMovieStreaming: MutableLiveData<List<Flatrate>> = MutableLiveData()
    val onSuccessMovieStreaming: LiveData<List<Flatrate>>
        get() = _onSuccessMovieStreaming

    private val _onSuccessMovieComments: MutableLiveData<List<Comment>> = MutableLiveData()
    val onSuccessMovieComments: LiveData<List<Comment>>
        get() = _onSuccessMovieComments

    private val _onSuccessMovieWatched: MutableLiveData<String> = MutableLiveData()
    val onSuccessMovieWatched: LiveData<String>
        get() = _onSuccessMovieWatched

    private val _onSuccessMovieSaved: MutableLiveData<String> = MutableLiveData()
    val onSuccessMovieSaved: LiveData<String>
        get() = _onSuccessMovieSaved

    fun getMovieDetailsScreen(movieId: Int) {
        viewModelScope.let {
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieById(movieId) },
                    onSuccess = {
                        _onSuccessMovieDetails.postValue(it as Result)
                    }
                )
            }
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieStreamings(movieId) },
                    onSuccess = {
                        _onSuccessMovieStreaming.postValue((it as List<*>).filterIsInstance<Flatrate>())
                    }
                )
            }
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieStatusId(movieId) },
                    onSuccess = {
                        val data = it as FeelMeMovieStatus
                        it.movieDetails?.let {
                            if(it == "watched") _onSuccessMovieWatched.postValue(it)
                            else if(it == "saved") _onSuccessMovieSaved.postValue(it)
                        }
                    }
                )
            }
            it.launch {
                callApi(
                    suspend { movieDetailsUseCase.getMovieComments(movieId) },
                    onSuccess = {
                        val data = it as FeelMeComments
                        val comments: MutableList<Comment> = mutableListOf()

                        data.comments.forEach { userComment ->
                            val photoUrl = if(userComment.uid.photoUrl.isNullOrEmpty()) null else Uri.parse(userComment.uid.photoUrl)

                            comments.add(
                                Comment(
                                    photoUrl,
                                    userComment.comment,
                                    userComment.uid.uid
                                )
                            )
                        }

                        _onSuccessMovieComments.postValue(comments.toList())
                    }
                )
            }
        }
    }

    fun saveUnwatchedMovie(movieId: Int, movieDetails: FeelMeMovie) {
        val data = workDataOf(MOVIE_ID to movieId, MOVIE_BACKDROP_PATH to movieDetails.backdropPath, MOVIE_TITLE to movieDetails.title )
        val saveUnwatchedMovieRequest: WorkRequest = OneTimeWorkRequestBuilder<SaveUnwatchedMovieService>().setInputData(data).build()
        WorkManager.getInstance(context).enqueue(saveUnwatchedMovieRequest)
    }

    fun saveWatchedMovie(movieId: Int, movieDetails: FeelMeMovie) {
        val data = workDataOf(MOVIE_ID to movieId, MOVIE_BACKDROP_PATH to movieDetails.backdropPath, MOVIE_TITLE to movieDetails.title )
        val saveWatchedMovieRequest: WorkRequest = OneTimeWorkRequestBuilder<SaveWatchedMovieService>().setInputData(data).build()
        WorkManager.getInstance(context).enqueue(saveWatchedMovieRequest)
    }

    fun removeMovie(movieId: Int) {
        val data = workDataOf(MOVIE_ID to movieId)
        val removeMovieRequest: WorkRequest = OneTimeWorkRequestBuilder<RemoveMovieService>().setInputData(data).build()
        WorkManager.getInstance(context).enqueue(removeMovieRequest)
    }

    fun saveComment(movieId: Int, comment: FeelMeMovieComment) {
        if(!comment.comment.isNullOrEmpty()) {
            val data = workDataOf(MOVIE_ID to movieId, COMMENT to comment.comment, MOVIE_BACKDROP_PATH to comment.backdropPath)
            val postMovieComment: WorkRequest = OneTimeWorkRequestBuilder<PostCommentService>().setInputData(data).build()
            WorkManager.getInstance(context).enqueue(postMovieComment)
        }
    }
}