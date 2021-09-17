package com.feelme.feelmeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class StreamDetails(
    @SerializedName("provider_id")
    @PrimaryKey val providerId: Int,
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    var logoPath: String,
    @SerializedName("provider_name")
    val providerName: String,
    var selected: Boolean = false,
)