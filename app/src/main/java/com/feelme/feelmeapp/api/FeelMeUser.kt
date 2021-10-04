package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import retrofit2.Response
import retrofit2.http.*

interface FeelMeUser {
    @GET("myprofile")
    suspend fun getMyProfile(): Response<UserProfileData>

    @POST("user")
    suspend fun saveMyProfile(@Body body: FeelMeNewUserPost): Response<Any>

    @GET("myprofile/unwatchedmovies")
    suspend fun getUmwatchedMovies(
        @Query("page") page: Int
    ): Response<MyMoviesList>

    @POST("movie/{movie_id}/add")
    suspend fun saveUnwatchedMovie(
        @Path("movie_id") id: Int,
        @Body body: FeelMeMovie
    ): Response<MyMoviesListItem>
}