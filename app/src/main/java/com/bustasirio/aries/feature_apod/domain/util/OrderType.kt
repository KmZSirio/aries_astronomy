package com.bustasirio.aries.feature_apod.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
