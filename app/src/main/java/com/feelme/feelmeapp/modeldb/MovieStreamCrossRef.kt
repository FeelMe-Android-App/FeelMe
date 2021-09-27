package com.feelme.feelmeapp.modeldb

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "provider_id"])
data class MovieStreamCrossRef(
    val movieId: Int,
    @ColumnInfo(name = "provider_id")
    val providerId: Int
)