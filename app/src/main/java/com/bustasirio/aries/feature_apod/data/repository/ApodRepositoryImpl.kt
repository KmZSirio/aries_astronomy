package com.bustasirio.aries.feature_apod.data.repository

import com.bustasirio.aries.feature_apod.data.remote.ApodApi
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val api: ApodApi
): ApodRepository {

    override suspend fun getApodList(
        apiKey: String,
        startDate: String,
        endDate: String,
        thumbs: Boolean
    ): List<Apod> {
        return api.getApodList(apiKey, startDate, endDate, thumbs)
    }

    override suspend fun getApod(apiKey: String, date: String, thumbs: Boolean): Apod {
        return api.getApod(apiKey, date, thumbs)
    }
}