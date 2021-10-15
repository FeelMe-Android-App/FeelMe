package com.feelme.feelmeapp.modeldb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userStream")
data class UserStreamList(
    @PrimaryKey
    val streamId: Int
)