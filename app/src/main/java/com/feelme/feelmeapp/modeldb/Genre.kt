package com.feelme.feelmeapp.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "genre")
@Parcelize
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "genreId")
    val id: Int,
    val name: String
): Parcelable

fun Genre.toGenreApi(): com.feelme.feelmeapp.model.Genre {
    return com.feelme.feelmeapp.model.Genre(
        id = this.id,
        name = this.name
    )
}