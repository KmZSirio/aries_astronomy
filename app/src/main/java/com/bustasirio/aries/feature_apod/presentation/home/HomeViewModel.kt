package com.bustasirio.aries.feature_apod.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bustasirio.aries.common.util.Constants.APOD_PAGE_SIZE
import com.bustasirio.aries.common.util.ESL
import com.bustasirio.aries.common.util.Resource
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.use_case.GetApodList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getApodList: GetApodList
) : ViewModel() {

    var apodsState = mutableStateOf<List<Apod>>(listOf())
    var errorState = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var endDate = org.joda.time.LocalDate.now()
    private var startDate = endDate.minusDays(APOD_PAGE_SIZE)

    init { getApods() }

    fun getApods() {
        getApodList(
            ESL,
            "$startDate",
            "$endDate",
            true
        ).onEach { result ->
            when (result) {
                is Resource.Success -> getApodsSuccess(result)
                is Resource.Error -> {
                    errorState.value = result.message ?: "An unexpected error occurred"
                    isLoading.value = false
                }
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }

    private fun getApodsSuccess(result: Resource<List<Apod>>) {
        endDate = startDate.minusDays(1)
        startDate = endDate.minusDays(APOD_PAGE_SIZE)

        var resultApods = result.data ?: emptyList()
        resultApods = resultApods.sortedByDescending { it.date }

        apodsState.value += resultApods
        isLoading.value = false
        Log.d("tagHomeVM", "${apodsState.value.size}")
    }
}