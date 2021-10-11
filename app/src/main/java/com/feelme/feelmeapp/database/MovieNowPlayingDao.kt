package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.feelme.feelmeapp.modeldb.MovieNowPlaying

@Dao
interface MovieNowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieList: List<MovieNowPlaying>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieNowPlaying)

    @Delete
    suspend fun delete(movie: MovieNowPlaying)
}
