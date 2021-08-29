package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.Movie
import com.feelme.feelmeapp.model.NowPlaying
import com.feelme.feelmeapp.model.Stream
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") id: Int): Response<Movie>

    @GET("watch/providers/movie?watch_region=BR")
    suspend fun getStreamList(): Response<Stream>

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