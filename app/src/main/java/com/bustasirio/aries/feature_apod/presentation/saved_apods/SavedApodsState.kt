package com.bustasirio.aries.feature_apod.presentation.saved_apods

import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.util.ApodOrder
import com.bustasirio.aries.feature_apod.domain.util.OrderType

data class SavedApodsState(
    val apods: List<SavedApod> = emptyList(),
    val apodOrder: ApodOrder = ApodOrder.SaveDate(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
