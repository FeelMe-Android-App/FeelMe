package com.feelme.feelmeapp.model.feelmeapi

data class FeelMeFriendsMovies(
    val friendsMovies: List<FriendMovieItem>
)

data class FriendMovieItem(
    val _id: String,
    val uid: String,
    val id: Int,
    val title: String,
    var backdropPath: String,
)