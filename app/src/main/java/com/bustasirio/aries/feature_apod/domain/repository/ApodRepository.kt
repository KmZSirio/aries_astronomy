package com.bustasirio.aries.feature_apod.domain.repository

import com.bustasirio.aries.feature_apod.domain.model.Apod

interface ApodRepository {

    suspend fun getApodList(
        apiKey: String,
        startDate: String,
        endDate: String,
        thumbs: Boolean
    ): List<Apod>

    suspend fun getApod(apiKey: String, date: String, thumbs: Boolean): Apod
}