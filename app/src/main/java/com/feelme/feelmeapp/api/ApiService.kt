package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.BuildConfig
import com.feelme.feelmeapp.utils.ConstantApp.Api.API_TOKEN
import com.feelme.feelmeapp.utils.ConstantApp.Api.API_TOKEN_KEY
import com.feelme.feelmeapp.utils.ConstantApp.Api.BASE_URL
import com.feelme.feelmeapp.utils.ConstantApp.Api.QUERY_PARAM_LANGUAGE_KEY
import com.feelme.feelmeapp.utils.ConstantApp.Api.QUERY_PARAM_LANGUAGE_VALUE
import com.feelme.feelmeapp.utils.ConstantApp.Api.QUERY_PARAM_WATCH_REGION
import com.feelme.feelmeapp.utils.ConstantApp.Api.QUERY_PARAM_WATCH_REGION_VALUE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    val tmdbApi: TMDBApi = getTMDBApiClient().create(TMDBApi::class.java)

    fun getTMDBApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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
                    .addQueryParameter(API_TOKEN_KEY, API_TOKEN)
                    .addQueryParameter(QUERY_PARAM_LANGUAGE_KEY, QUERY_PARAM_LANGUAGE_VALUE)
                    .addQueryParameter(QUERY_PARAM_WATCH_REGION, QUERY_PARAM_WATCH_REGION_VALUE)
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
        return interceptor.build()
    }
}