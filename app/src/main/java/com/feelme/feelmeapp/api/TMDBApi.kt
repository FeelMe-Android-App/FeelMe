package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") id: Int): Response<Result>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieStreaming(@Path("movie_id") movieId: Int): Response<MovieStreamings>

    @GET("watch/providers/movie?watch_region=BR")
    suspend fun getStreamList(): Response<Stream>

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("with_watch_providers") providers: String,
        @Query("with_genres") genres: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): Response<DiscoverMovies>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<Search>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<Genres>

}