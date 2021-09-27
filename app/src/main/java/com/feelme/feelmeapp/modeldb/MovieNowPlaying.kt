package com.feelme.feelmeapp.modeldb

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "NowPlayingDb")
@Parcelize
data class MovieNowPlaying(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieIdNowPlaying: Int
): Parcelable