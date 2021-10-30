package com.bustasirio.aries.feature_apod.domain.util

sealed class ApodOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): ApodOrder(orderType)
    class SaveDate(orderType: OrderType): ApodOrder(orderType)
    class PublishDate(orderType: OrderType): ApodOrder(orderType)

    fun copy(orderType: OrderType): ApodOrder {
        return when (this) {
            is Title -> Title(orderType)
            is SaveDate -> SaveDate(orderType)
            is PublishDate -> PublishDate(orderType)
        }
    }
}