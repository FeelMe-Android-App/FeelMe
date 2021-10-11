package com.feelme.feelmeapp.model

data class UserProfileData(
    val _id: String,
    val email: String,
    val followCount: Int,
    val followedCount: Int,
    val name: String,
    val photoUrl: String,
    val uid: String,
)