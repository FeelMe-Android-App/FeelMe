package com.feelme.feelmeapp.modeldb

import androidx.room.Embedded
import androidx.room.Relation
import com.feelme.feelmeapp.model.Result

data class MovieAndNowPlaying(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieIdNowPlaying"
    )
    val nowPlaying: MovieNowPlaying?
)