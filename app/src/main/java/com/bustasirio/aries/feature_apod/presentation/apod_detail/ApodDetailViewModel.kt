package com.bustasirio.aries.feature_apod.presentation.apod_detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import com.bustasirio.aries.feature_apod.domain.use_case.ApodUsesCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodDetailViewModel @Inject constructor(
    private val usesCases: ApodUsesCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val savedState = mutableStateOf(false)
    private var savedApod: SavedApod? = null

    init {
        savedStateHandle.get<String>("date")?.let { date ->
            viewModelScope.launch {
                savedState.value = isSaved(date) != null
            }
        }
    }

    private suspend fun isSaved(date: String): SavedApod? {
        val result = usesCases.isSavedApod(date)
        savedApod = result
        return result
    }

    suspend fun insertApod(apod: Apod) {
        val newSaved = SavedApod(
            apod = apod,
            timestamp = System.currentTimeMillis(),
            id = apod.date
        )

        usesCases.insertSavedApod( newSaved )

        savedApod = newSaved
    }

    suspend fun deleteApod() {
        if (savedApod != null) {
            Log.d("tagDetailVM", "Deleted: ${savedApod?.apod?.date}")
            usesCases.deleteSavedApod( savedApod!! )
        }
        savedApod = null
    }
}