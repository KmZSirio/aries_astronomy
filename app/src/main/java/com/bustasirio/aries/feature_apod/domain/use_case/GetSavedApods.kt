package com.bustasirio.aries.feature_apod.domain.use_case

import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import com.bustasirio.aries.feature_apod.domain.util.ApodOrder
import com.bustasirio.aries.feature_apod.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSavedApods(
    private val repository: ApodRepository
) {

    operator fun invoke(
        apodOrder: ApodOrder = ApodOrder.SaveDate(OrderType.Descending)
    ): Flow<List<SavedApod>> {
        return repository.getSavedApods().map { apods ->
            when (apodOrder.orderType) {
                is OrderType.Ascending -> {
                    when (apodOrder) {
                        is ApodOrder.Title -> apods.sortedBy { it.apod.title.lowercase() }
                        is ApodOrder.SaveDate -> apods.sortedBy { it.timestamp }
                        is ApodOrder.PublishDate -> apods.sortedBy { it.apod.date }
                    }
                }
                is OrderType.Descending -> {
                    when (apodOrder) {
                        is ApodOrder.Title -> apods.sortedByDescending { it.apod.title.lowercase() }
                        is ApodOrder.SaveDate -> apods.sortedByDescending { it.timestamp }
                        is ApodOrder.PublishDate -> apods.sortedByDescending { it.apod.date }
                    }
                }
            }
        }
    }
}