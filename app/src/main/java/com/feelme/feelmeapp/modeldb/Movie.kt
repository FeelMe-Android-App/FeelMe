package com.feelme.feelmeapp.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.feelme.feelmeapp.model.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val overview: String,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    val title: String,
) : Parcelable