package com.feelme.feelmeapp.model

data class UserResponseFeelMe(
    val name: String,
    val email: String,
    val status: String,
    val photoUrl: String,
    val user_id: String,
    val streaming: List<Any>,
    val follow: List<String>,
    val follower: List<String>,
)