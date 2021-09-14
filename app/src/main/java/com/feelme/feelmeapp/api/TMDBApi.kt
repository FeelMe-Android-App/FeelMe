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
    suspend fun getMovieById(@Path("movie_id") id: Int): Response<Movie>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieStreamings(@Path("movie_id") movieId: Int): Response<MovieStreamings>

    @GET("watch/providers/movie?watch_region=BR")
    suspend fun getStreamList(): Response<Stream>

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("with_watch_providers") providers: String,
        @Query("with_genres") genres: String,
        @Query("sort_by") sortBy: String
    ): Response<DiscoverMovies>

//    DEIXEI COMO EXEMPLO PARA AS FUTURAS CHAMADAS

//    @GET("movie/popular")
//    suspend fun getPopularMovies(): Response<Popular>
//
//    @GET("movie/{movie_id}")
//    suspend fun getMovieById(
//        @Path("movie_id") movieId: Int
//    ): Response<Movie>
//
//    @POST("movie/save")
//    suspend fun saveMovie(
//        @Body movie: Movie
//    ): Response<ResponseBody>

}