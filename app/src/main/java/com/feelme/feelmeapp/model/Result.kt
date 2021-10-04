package com.feelme.feelmeapp.model

import androidx.recyclerview.widget.DiffUtil
import com.feelme.feelmeapp.modeldb.Movie
import com.feelme.feelmeapp.modeldb.MovieNowPlaying
import com.google.gson.annotations.SerializedName

data class Result(
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genres")
    var genreIds: List<Genre>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String,
    val title: String,
    val video: Boolean,
    val runtime: Int,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Result> =
            object : DiffUtil.ItemCallback<Result>() {
                override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}

fun Result.toMovieDb(): Movie {
    return Movie(
        id = this.id,
        adult = this.adult ?: false,
        backdropPath = this.backdropPath ?: "",
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate ?: "",
        runtime = this.runtime,
        title = this.title
    )
}

fun Result.toMovieNowPlaying(): MovieNowPlaying {
    return MovieNowPlaying(
        id = this.id.toLong(),
        movieIdNowPlaying = this.id
    )
}

