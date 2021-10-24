package com.feelme.feelmeapp.model.feelmeapi

data class FeelMeUserProfile(
    val userprofile: FeelMeUserProfileData
)

data class FeelMeUserProfileData(
    val follow: Int,
    val follwed: Int,
    val uid: String,
    val name: String,
    val email: String?,
    val photoUrl: String,
    val streaming: Int,
    val watched: Int,
    val unwatched: Int,
    val lastwatched: List<LastWatchedMovies>,
    val lastcomments: List<LastComments>?,
)

data class LastWatchedMovies(
    val uid: String,
    val id: Int,
    val title: String,
    var backdropPath: String,
)

data class LastComments(
    val uid: String,
    val comment: String,
    val movieId: Int,
    var backdropPath: String,
    var photoUrl: String?,
)