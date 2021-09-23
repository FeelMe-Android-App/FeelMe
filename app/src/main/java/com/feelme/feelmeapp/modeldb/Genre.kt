package com.feelme.feelmeapp.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "genre")
@Parcelize
data class Genre(
    @PrimaryKey val id: Int,
    val name: String
): Parcelable