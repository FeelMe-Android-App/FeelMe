package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.BuildConfig
import com.feelme.feelmeapp.firebase.UserProfile
import com.feelme.feelmeapp.utils.ConstantApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FeelMeApiService {
    val feelMeApiService: FeelMeUser = getFeelMeApiClient().create(FeelMeUser::class.java)

    fun getFeelMeApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantApp.Api.BASE_URL_FEELME)
            .client(getInterceptorClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .build()
                val newRequest = chain.request().newBuilder().url(url).header("Authorization", "Bearer ${UserProfile.currentUser.value?.token ?: ""}").build()
                chain.proceed(newRequest)
            }
        return interceptor.build()
    }
}