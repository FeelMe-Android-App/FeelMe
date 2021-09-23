package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import com.feelme.feelmeapp.modeldb.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<Genre>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(genreList: List<Genre>)

    @Insert(onConflict = REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Delete
    suspend fun deleteGenre(genre: Genre)
}