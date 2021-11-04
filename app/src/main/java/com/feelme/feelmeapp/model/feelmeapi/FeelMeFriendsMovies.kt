package com.feelme.feelmeapp.model.feelmeapi

import com.feelme.feelmeapp.features.movieDetails.adapter.FeelMeCommentUserDetails

data class FeelMeFriendsMovies(
    val friendsMovies: List<FriendMovieItem>
)

data class FriendMovieItem(
    val _id: String,
    val uid: FeelMeCommentUserDetails,
    val id: Int,
    val title: String,
    var backdropPath: String,
)