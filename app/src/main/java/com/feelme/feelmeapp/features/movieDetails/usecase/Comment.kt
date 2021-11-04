package com.feelme.feelmeapp.features.movieDetails.usecase

import android.net.Uri

data class Comment (
    val image: Uri?,
    val comment: String,
    val profileId: String,
    val _id: String,
)