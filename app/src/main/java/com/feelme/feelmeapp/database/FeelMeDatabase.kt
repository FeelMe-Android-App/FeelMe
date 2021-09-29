package com.feelme.feelmeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feelme.feelmeapp.modeldb.*

object FeelMeDatabase {
    @Database(entities = [Movie::class, Genre::class, Stream::class, MovieGenreCrossRef::class, MovieStreamCrossRef::class, MovieNowPlaying::class], version = 1)
    abstract class FeelMeRoomDatabase : RoomDatabase() {
        abstract fun movieDao(): MovieDao
        abstract fun genreDao(): GenreDao
        abstract fun streamDao(): StreamDao
        abstract fun movieGenreDao(): MovieGenreCrossRefDao
        abstract fun movieStreamDao(): MovieStreamCrossRefDao
        abstract fun movieNowPlaying(): MovieNowPlayingDao
        abstract fun nowPlaying(): NowPlayingDao
    }

    fun getDatabase(context: Context) : FeelMeRoomDatabase {
        return Room.databaseBuilder(
            context,
            FeelMeRoomDatabase::class.java, "feelme"
        ).build()
    }
}