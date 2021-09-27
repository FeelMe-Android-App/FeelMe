package com.feelme.feelmeapp.modeldb

import androidx.room.Embedded
import androidx.room.Relation

data class StreamWithMovies(
    @Embedded val stream: MovieStream,
    @Relation(
        entity = Movie::class,
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val movieStreamGenre: List<MovieGenre>
)