package com.feelme.feelmeapp.model.feelmeapi

data class FeelMePostComment(
    val uid: String,
    val name: String,
    val photoUrl: String,
    val comment: String,
    val movieId: Int,
    var backdropPath: String,
    val createdAt: String,
    val updatedAt: String,
    val _id: String,
)