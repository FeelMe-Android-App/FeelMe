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
    val backdropPath: String? = "",
    val overview: String,
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    val title: String,
) : Parcelable

fun Movie.toResultApi(): Result {
    return Result(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = listOf(),
        originalLanguage = "",
        originalTitle = "",
        overview = this.overview,
        popularity = 0.00,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = false,
        voteAverage = 0.00,
        voteCount = 0,
        runtime = this.runtime
    )
}