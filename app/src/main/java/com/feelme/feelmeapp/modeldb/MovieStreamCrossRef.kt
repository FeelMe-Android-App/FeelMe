package com.feelme.feelmeapp.modeldb

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "providerId"])
data class MovieStreamCrossRef(
    val movieId: Int,
    val providerId: Int
)