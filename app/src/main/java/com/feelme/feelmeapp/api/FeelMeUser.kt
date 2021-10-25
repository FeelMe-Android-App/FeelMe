package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.feelmeapi.*
import com.feelme.feelmeapp.model.feelmeapi.FeelMeUserProfile
import retrofit2.Response
import retrofit2.http.*

interface FeelMeUser {
    @GET("myprofile")
    suspend fun getMyProfile(): Response<UserProfileData>

    @POST("user")
    suspend fun saveMyProfile(@Body body: FeelMeNewUserPost): Response<Any>

    @GET("myprofile/unwatchedmovies")
    suspend fun getUnwatchedMovies(
        @Query("page") page: Int
    ): Response<MyMoviesList>

    @GET("myprofile/watchedmovies")
    suspend fun getWatchedMovies(
        @Query("page") page: Int
    ): Response<MyMoviesList>

    @POST("movie/{movie_id}/add")
    suspend fun saveUnwatchedMovie(
        @Path("movie_id") id: Int,
        @Body body: FeelMeMovie
    ): Response<MyMoviesListItem>

    @POST("movie/{movie_id}/watched")
    suspend fun saveWatchedMovie(
        @Path("movie_id") id: Int,
        @Body body: FeelMeMovie
    ): Response<MyMoviesListItem>

    @DELETE("movie/{movie_id}")
    suspend fun removeMovie(
        @Path("movie_id") id: Int
    ): Response<Any>

    @POST("feeling/{feelingId}/{movieId}/vote")
    suspend fun voteMovie(
        @Path("feelingId") feelingId: Int,
        @Path("movieId") movieId: Int,
    ): Response<Any>

    @GET("movie/{movie_id}")
    suspend fun getMovieStatus(
        @Path("movie_id") id: Int,
    ): Response<FeelMeMovieStatus>

    @GET("comment/{movie_id}")
    suspend fun getMovieComments(
        @Path("movie_id") id: Int,
    ): Response<FeelMeComments>

    @POST("comment/{movie_id}")
    suspend fun postMovieComment(
        @Path("movie_id") movieId: Int,
        @Body body: FeelMeMovieComment
    ): Response<Any>

    @POST("")
    suspend fun saveStream(
        @Body body: List<Int>
    ): Response<Any>

    @GET("user")
    suspend fun getSearchFriend(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<FeelMeUsersSearch>

    @GET("user/{user_id}")
    suspend fun getUserProfile(
        @Path("user_id") uid: String
    ): Response<FeelMeUserProfile>

    @GET("user/{user_id}/lastmovies")
    suspend fun getUserLastWatchedMovies(
        @Path("user_id") uid: String
    ): Response<MyMoviesList>

    @GET("user/{user_id}/lastcomments")
    suspend fun getUserLastComments(
        @Path("user_id") uid: String
    ): Response<FeelMeComments>

    @POST("user/{user_id}/follow")
    suspend fun followUser(
        @Path("user_id") uid: String
    ): Response<Any>

    @POST("user/{user_id}/unfollow")
    suspend fun unfollowUser(
        @Path("user_id") uid: String
    ): Response<Any>

    @GET("friendsMovies")
    suspend fun getFriendsStatus(): Response<FeelMeFriendsMovies>

    @GET("friendscomment")
    suspend fun getFriendsComments(
        @Query("page") page: Int,
    ): Response<FeelMeComments>
}