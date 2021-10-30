package com.bustasirio.aries.feature_apod.domain.use_case

import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository

class IsSavedApod(
    private val repository: ApodRepository
) {

    suspend operator fun invoke(date: String): SavedApod? {
        return repository.isSavedByDate(date)
    }
}