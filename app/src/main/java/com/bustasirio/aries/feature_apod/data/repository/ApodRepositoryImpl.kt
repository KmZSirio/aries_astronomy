package com.bustasirio.aries.feature_apod.data.repository

import com.bustasirio.aries.feature_apod.data.local.ApodDao
import com.bustasirio.aries.feature_apod.data.remote.ApodApi
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val api: ApodApi,
    private val dao: ApodDao
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

    override fun getSavedApods(): Flow<List<SavedApod>> {
        return dao.getSavedApods()
    }

    override suspend fun insertSavedApod(savedApod: SavedApod) {
        return dao.insertSavedApod(savedApod = savedApod)
    }

    override suspend fun deleteSavedApod(savedApod: SavedApod) {
        return dao.deleteSavedApod(savedApod = savedApod)
    }

    override suspend fun isSavedByDate(date: String): SavedApod? {
        return dao.isSavedByDate(date)
    }
}