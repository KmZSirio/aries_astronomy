package com.bustasirio.aries.feature_apod.presentation.saved_apods

import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.util.ApodOrder

sealed class SavedApodsEvent{
    data class Order(val apodOrder: ApodOrder): SavedApodsEvent()
    data class DeleteSavedApod(val apod: SavedApod): SavedApodsEvent()
    object ToggleOrderSection: SavedApodsEvent()
}
