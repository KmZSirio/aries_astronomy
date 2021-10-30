package com.bustasirio.aries.feature_apod.presentation.saved_apods

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bustasirio.aries.feature_apod.domain.use_case.ApodUsesCases
import com.bustasirio.aries.feature_apod.domain.util.ApodOrder
import com.bustasirio.aries.feature_apod.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedApodsViewModel @Inject constructor(
    private val usesCases: ApodUsesCases
): ViewModel() {

    private val _state = mutableStateOf(SavedApodsState())
    val state: State<SavedApodsState> = _state

    private var getSavedApodsJob: Job? = null

    init {
        getSavedApods(ApodOrder.SaveDate(OrderType.Descending))
    }

    fun onEvent(event: SavedApodsEvent) {
        when (event) {
            is SavedApodsEvent.Order -> {
                if (state.value.apodOrder::class == event.apodOrder::class &&
                    state.value.apodOrder.orderType == event.apodOrder.orderType
                ) { return }
                getSavedApods(event.apodOrder)
            }
            is SavedApodsEvent.DeleteSavedApod -> {
                viewModelScope.launch {
                    usesCases.deleteSavedApod(event.apod)
                }
            }
            is SavedApodsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getSavedApods(apodOrder: ApodOrder) {
        getSavedApodsJob?.cancel()

        getSavedApodsJob = usesCases.getSavedApods(apodOrder)
            .onEach { apods ->
                _state.value = state.value.copy(
                    apods = apods,
                    apodOrder = apodOrder
                )
            }.launchIn(viewModelScope)
    }
}