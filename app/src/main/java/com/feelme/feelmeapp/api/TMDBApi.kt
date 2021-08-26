package com.feelme.feelmeapp.api

import com.feelme.feelmeapp.model.NowPlaying
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {
    @GET("movie/now_playing?api_key=3fdab48e2bddf5d597050debe64abb1c")
    suspend fun getNowPlayingMovies(): Response<NowPlaying>

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