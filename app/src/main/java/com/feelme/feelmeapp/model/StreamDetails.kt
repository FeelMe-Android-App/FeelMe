package com.feelme.feelmeapp.model

import com.feelme.feelmeapp.modeldb.Stream
import com.google.gson.annotations.SerializedName

data class StreamDetails(
    @SerializedName("provider_id")
    val providerId: Int,
    @SerializedName("display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    var logoPath: String,
    @SerializedName("provider_name")
    val providerName: String,
    var selected: Boolean = false,
)

fun StreamDetails.toStreamDb(): Stream {
    return Stream(
        providerId = this.providerId,
        displayPriority = this.displayPriority,
        logoPath = this.logoPath,
        providerName = this.providerName,
    )
}