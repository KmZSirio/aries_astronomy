package com.bustasirio.aries.common

import android.webkit.MimeTypeMap
import com.bustasirio.aries.feature_apod.domain.model.Apod

fun formatDate(date: String): String {
    // * 0123-56-89

    val month = when(date.substring(5, 7)) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Ago"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> "-" + date.substring(5, 7)
    }

    return date.substring(8) + " " + month
}

fun fetchMimeTypeFromUrl(url: String): String? {
    var type: String? = null
    try {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
    } catch (e: Exception) {

    }
    return type
}

fun getUrlFromApod(apod: Apod): String {
    return if (apod.mediaType == "image") apod.hdurl ?: apod.url
    else  apod.thumbnailUrl!!
}