package com.feelme.feelmeapp.modeldb

import androidx.room.Entity

@Entity(primaryKeys = ["movieId","genreId"])
data class MovieGenreTable(
    val movieId: Int,
    val genreId: Int,
)