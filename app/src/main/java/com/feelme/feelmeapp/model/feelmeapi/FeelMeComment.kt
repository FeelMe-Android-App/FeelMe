package com.feelme.feelmeapp.model.feelmeapi

import com.feelme.feelmeapp.features.movieDetails.adapter.FeelMeCommentUserDetails

data class FeelMeComment(
    val _id: String,
    val uid: FeelMeCommentUserDetails,
    val comment: String,
    val movieId: Int,
    var backdropPath: String,
    val createdAt: String,
    val updatedAt: String,
)