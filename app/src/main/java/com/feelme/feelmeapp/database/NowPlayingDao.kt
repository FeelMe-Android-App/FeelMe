package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.feelme.feelmeapp.modeldb.MovieAndNowPlaying

@Dao
interface NowPlayingDao {
    @Transaction
    @Query("SELECT * FROM NowPlayingDb")
    suspend fun getNowPlaying(): List<MovieAndNowPlaying>
}