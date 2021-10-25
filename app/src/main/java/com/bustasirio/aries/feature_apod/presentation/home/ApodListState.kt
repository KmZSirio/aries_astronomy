package com.bustasirio.aries.feature_apod.presentation.home

import com.bustasirio.aries.feature_apod.domain.model.Apod

data class ApodListState(
    val isLoading: Boolean = false,
    val apods: List<Apod> = emptyList(),
    val error: String = ""
)
