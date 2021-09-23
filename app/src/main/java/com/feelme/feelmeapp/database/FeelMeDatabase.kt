package com.feelme.feelmeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feelme.feelmeapp.modeldb.Genre

object FeelMeDatabase {
    @Database(entities = [Genre::class], version = 1)
    abstract class FeelMeRoomDatabase : RoomDatabase() {
        abstract fun genreDao(): GenreDao
    }

    fun getDatabase(context: Context) : FeelMeRoomDatabase {
        return Room.databaseBuilder(
            context,
            FeelMeRoomDatabase::class.java, "feelme"
        ).build()
    }
}