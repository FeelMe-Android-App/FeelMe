package com.feelme.feelmeapp.database

import androidx.room.*
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.MovieGenre
import com.feelme.feelmeapp.modeldb.MovieStream
import com.feelme.feelmeapp.modeldb.StreamWithMovies

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun getMovieStreamGenre(movieId: Int): StreamWithMovies

    @Transaction
    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun getMovieGenre(movieId: Int): MovieGenre

    @Transaction
    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun getMovieStream(movieId: Int): MovieStream

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%'")
    suspend fun getMovieByQuery(query: String): List<Movie>

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieList: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}