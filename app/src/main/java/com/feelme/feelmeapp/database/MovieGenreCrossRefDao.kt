package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.feelme.feelmeapp.modeldb.MovieGenreCrossRef

@Dao
interface MovieGenreCrossRefDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovieGenre(moviesGenresList: List<MovieGenreCrossRef>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovieGenre(movieGenre: MovieGenreCrossRef)

    @Delete
    suspend fun delete(movieGenre: MovieGenreCrossRef)
}