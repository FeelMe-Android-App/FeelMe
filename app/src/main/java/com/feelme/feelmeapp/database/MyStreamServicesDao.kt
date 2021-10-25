package com.feelme.feelmeapp.database

import androidx.room.*
import com.feelme.feelmeapp.modeldb.MovieGenreCrossRef
import com.feelme.feelmeapp.modeldb.StreamWithMovies
import com.feelme.feelmeapp.modeldb.UserStreamList
import com.feelme.feelmeapp.modeldb.UserStreamListWithStream

@Dao
interface MyStreamServicesDao {
    @Transaction
    @Query("SELECT * FROM userStream")
    suspend fun getUserStream(): List<UserStreamListWithStream>

    @Query("SELECT * FROM userStream")
    suspend fun getUserStreamIds(): List<UserStreamList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStream(stream: UserStreamList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStreams(streamList: List<UserStreamList>)

    @Delete
    suspend fun delete(stream: UserStreamList)
}