package com.bustasirio.aries.feature_apod.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apod(
    val copyright : String?,
    val date : String,
    val explanation : String,
    val hdurl : String?,
    @SerializedName("media_type")
    val mediaType : String,
    @SerializedName("service_version")
    val serviceVersion : String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl : String?,
    val title : String,
    val url : String
): Parcelable
