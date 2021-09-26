package com.feelme.feelmeapp.model

import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.NowPlaying
import com.google.gson.annotations.SerializedName

data class Result(
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun Result.toNowPlayingDb(): NowPlaying {
    return NowPlaying(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        runtime = 0,
        title = this.title
    )
}