package com.feelme.feelmeapp.modeldb

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieGenre(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genre: List<Genre>
)

data class GenreMovie(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movie: List<Movie>
)