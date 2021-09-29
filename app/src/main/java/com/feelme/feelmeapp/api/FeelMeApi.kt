package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.UserResponseFeelMe
import retrofit2.Response
import retrofit2.http.GET

interface FeelMeApi {
    @GET("myprofile")
    suspend fun getMyProfile(): Response<UserResponseFeelMe>
}