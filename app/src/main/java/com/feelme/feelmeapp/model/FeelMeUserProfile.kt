package com.feelme.feelmeapp.model

data class FeelMeUserProfile(
    val email: String,
    val follow: List<Follow>,
    val follower: List<Follow>,
    val name: String,
    val photoUrl: String,
    val status: String,
    val streaming: List<Int>,
    val uid: String
)