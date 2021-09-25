package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.feelme.feelmeapp.modeldb.MovieStreamCrossRef

@Dao
interface MovieStreamingCrossRefDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovieGenre(movieStreamingList: List<MovieStreamCrossRef>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovieGenre(movieStreaming: MovieStreamCrossRef)

    @Delete
    suspend fun delete(movieStreaming: MovieStreamCrossRef)
}