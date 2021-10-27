package com.bustasirio.aries.feature_apod.presentation.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bustasirio.aries.common.util.Constants.APOD_404_TEXT
import com.bustasirio.aries.common.util.Constants.APOD_PAGE_SIZE
import com.bustasirio.aries.common.util.ESL
import com.bustasirio.aries.common.util.Resource
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.use_case.GetApod
import com.bustasirio.aries.feature_apod.domain.use_case.GetApodList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getApodList: GetApodList,
    private val getApod: GetApod
) : ViewModel() {

    var apodState = mutableStateOf<Apod?>(null)
    var snackbarErrorState = mutableStateOf("")

    var apodsState = mutableStateOf<List<Apod>>(listOf())
    var errorState = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var page = 0

    private var endDate = org.joda.time.LocalDate.now()
    private var startDate = endDate.minusDays(APOD_PAGE_SIZE)

    init {
        getApods()
    }

    fun getApod(date: String) {
        getApod(
            ESL,
            date,
            true
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    apodState.value = result.data
                    isLoading.value = false
                }
                is Resource.Error -> {
                    val errorMsg = result.message ?: "An unexpected error occurred"
                    snackbarErrorState.value =
                        if (errorMsg == APOD_404_TEXT) "This day does not have an APOD"
                        else errorMsg
                    isLoading.value = false
                }
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }

    fun getApods() {
        page++
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
    }
}