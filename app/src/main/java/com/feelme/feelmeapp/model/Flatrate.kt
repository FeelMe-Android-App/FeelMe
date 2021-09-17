package com.feelme.feelmeapp.model

import com.google.gson.annotations.SerializedName

data class Flatrate(
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    var logoPath: String?,
    @SerializedName("provider_id")
    val providerId: Int,
    val provider_name: String
)