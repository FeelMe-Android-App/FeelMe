package com.feelme.feelmeapp.modeldb

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserStreamListWithStream(
    @Embedded val userStream: UserStreamList,
    @Relation(
        parentColumn = "streamId",
        entityColumn = "provider_id",
    )
    val stream: Stream
)