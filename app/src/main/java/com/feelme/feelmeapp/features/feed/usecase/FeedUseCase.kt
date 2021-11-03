package com.feelme.feelmeapp.features.feed.usecase

import com.feelme.feelmeapp.adapters.PagingMovieComments.PagedMovieCommentsModel
import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.feed.repository.FeedRepository
import com.feelme.feelmeapp.model.feelmeapi.FeelMeComments
import com.feelme.feelmeapp.model.feelmeapi.FeelMeFollow
import com.feelme.feelmeapp.model.feelmeapi.FeelMeFriendsMovies
import com.feelme.feelmeapp.utils.ResponseApi

class FeedUseCase(private val feedRepository: FeedRepository) {
    suspend fun getFriendsStatus(): ResponseApi {
        when(val responseApi = feedRepository.getFriendsStatus()) {
            is ResponseApi.Success -> {
                val friendsStatus = responseApi.data as? FeelMeFriendsMovies
                val lastWatched = friendsStatus?.friendsMovies?.map {
                    it.backdropPath = it.backdropPath.getFullImageUrl()
                    it
                }
                return ResponseApi.Success(lastWatched)
            }
            is ResponseApi.Error -> {
                return responseApi
            }
        }
    }

    fun setupMovieCommentsList(list: FeelMeComments?): List<PagedMovieCommentsModel> {
        val pagedMovieCommentsList: MutableList<PagedMovieCommentsModel> = mutableListOf()
        list?.comments?.forEach {
            val backdrop = if(it.backdropPath.isNullOrEmpty()) "null" else it.backdropPath
            val photoUrl = if(it.uid.photoUrl.isNullOrEmpty()) "null" else it.uid.photoUrl

            pagedMovieCommentsList.add(
                PagedMovieCommentsModel(
                    backdropPath = backdrop,
                    comment = it.comment,
                    profilePhoto = photoUrl,
                    uid = it.uid.uid,
                    movieId = it.movieId
                )
            )
        }
        return pagedMovieCommentsList
    }

    suspend fun getUserFollow(): ResponseApi {
        when(val responseApi = feedRepository.getUserFollow()) {
            is ResponseApi.Success -> {
                val data = responseApi.data as FeelMeFollow
                return ResponseApi.Success(data)
            }
            else -> return responseApi
        }
    }
}