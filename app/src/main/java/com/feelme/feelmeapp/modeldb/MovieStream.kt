package com.feelme.feelmeapp.modeldb

import androidx.room.*

data class MovieStream(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "provider_id",
        associateBy = Junction(MovieStreamCrossRef::class)
    )
    val stream: List<Stream>
)