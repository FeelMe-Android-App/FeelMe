package com.feelme.feelmeapp.features.feed.usecase

import com.feelme.feelmeapp.extensions.getFullImageUrl
import com.feelme.feelmeapp.features.feed.repository.FeedRepository
import com.feelme.feelmeapp.model.feelmeapi.FeelMeComments
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
                responseApi
                return responseApi
            }
        }
    }

    suspend fun getFriendsComments(page: Int): ResponseApi {
        when(val responseApi = feedRepository.getFriendsComments(page)) {
            is ResponseApi.Success -> {
                val friendsComments = responseApi.data as? FeelMeComments
                val lastComments = friendsComments?.comments?.map {
                    it.backdropPath = it.backdropPath.getFullImageUrl()
                    it
                }
                return ResponseApi.Success(lastComments)
            }
            else -> return responseApi
        }
    }
}