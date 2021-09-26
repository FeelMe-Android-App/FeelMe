package com.feelme.feelmeapp.database

import androidx.room.*
import com.feelme.feelmeapp.modeldb.NowPlaying

@Dao
interface NowPlayingDao {
    @Query("SELECT * FROM now_playing")
    suspend fun getAllMovies(): List<NowPlaying>

    @Query("SELECT * FROM now_playing WHERE movieId = :movieId")
    suspend fun getMovieById(movieId: Int): List<NowPlaying>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieList: List<NowPlaying>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: NowPlaying)

    @Delete
    suspend fun delete(movie: NowPlaying)
}