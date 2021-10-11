package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.*
import com.feelme.feelmeapp.model.feelmeapi.FeelMeComments
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovie
import com.feelme.feelmeapp.model.feelmeapi.FeelMeMovieStatus
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

    @GET("movie/{movie_id}")
    suspend fun getMovieStatus(
        @Path("movie_id") id: Int,
    ): Response<FeelMeMovieStatus>

    @GET("comment/{movie_id}")
    suspend fun getMovieComments(
        @Path("movie_id") id: Int,
    ): Response<FeelMeComments>
}