package com.feelme.feelmeapp.modeldb

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.feelme.feelmeapp.model.StreamDetails
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "stream")
@Parcelize
data class Stream (
    @PrimaryKey
    @SerializedName("provider_id")
    @ColumnInfo(name = "provider_id")
    val providerId: Int,
    @SerializedName("display_priority")
    @ColumnInfo(name = "display_priority")
    val displayPriority: Int,
    @SerializedName("logo_path")
    @ColumnInfo(name = "logo_path")
    var logoPath: String,
    @SerializedName("provider_name")
    @ColumnInfo(name = "provider_name")
    val providerName: String,
): Parcelable

fun Stream.toStreamDetails(): StreamDetails {
    return StreamDetails(
        providerId = this.providerId,
        displayPriority = this.displayPriority,
        logoPath = this.logoPath,
        providerName = this.providerName
    )
}