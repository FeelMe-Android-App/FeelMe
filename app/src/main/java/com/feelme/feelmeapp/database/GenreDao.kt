package com.feelme.feelmeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.feelme.feelmeapp.modeldb.Genre
import com.feelme.feelmeapp.modeldb.GenreMovie

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<Genre>

    @Query("SELECT * FROM genre WHERE genreId = :genreId LIMIT :limit OFFSET :offset")
    suspend fun getMovieByGenre(genreId: Int, limit: Int, offset: Int): GenreMovie

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(genreList: List<Genre>)

    @Insert(onConflict = REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Delete
    suspend fun deleteGenre(genre: Genre)
}