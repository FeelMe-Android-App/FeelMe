package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.feelme.feelmeapp.modeldb.MovieStreamCrossRef

@Dao
interface MovieStreamCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movieList: List<MovieStreamCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieStreamCrossRef)

    @Delete
    suspend fun delete(movie: MovieStreamCrossRef)
}
