package com.bustasirio.aries.feature_apod.domain.repository

import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import kotlinx.coroutines.flow.Flow

interface ApodRepository {

    suspend fun getApodList(
        apiKey: String,
        startDate: String,
        endDate: String,
        thumbs: Boolean
    ): List<Apod>

    suspend fun getApod(apiKey: String, date: String, thumbs: Boolean): Apod

    fun getSavedApods(): Flow<List<SavedApod>>

    suspend fun insertSavedApod(savedApod: SavedApod)

    suspend fun deleteSavedApod(savedApod: SavedApod)

    suspend fun isSavedByDate(date: String): SavedApod?
}