package com.feelme.feelmeapp.modeldb

import androidx.room.Embedded
import androidx.room.Relation

data class MovieAndNowPlaying(
    @Embedded val nowPlaying: MovieNowPlaying,
    @Relation(
        parentColumn = "movieIdNowPlaying",
        entityColumn = "movieId"
    )
    val movie: Movie
)