package com.feelme.feelmeapp.database

import androidx.room.*
import com.feelme.feelmeapp.modeldb.Stream

@Dao
interface StreamDao {
    @Query("SELECT * FROM stream")
    suspend fun getAllStream(): List<Stream>

    @Query("SELECT * FROM stream WHERE provider_id = :providerId")
    suspend fun getStreamById(providerId: Int): List<Stream>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(streamList: List<Stream>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stream: Stream)

    @Delete
    suspend fun delete(stream: Stream)
}