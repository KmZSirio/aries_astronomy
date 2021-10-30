package com.bustasirio.aries.feature_apod.data.local

import androidx.room.*
import com.bustasirio.aries.common.util.Constants.SAVED_APODS_TABLE_NAME
import com.bustasirio.aries.feature_apod.domain.model.SavedApod
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Query("SELECT * FROM $SAVED_APODS_TABLE_NAME")
    fun getSavedApods(): Flow<List<SavedApod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedApod(savedApod: SavedApod)

    @Delete
    suspend fun deleteSavedApod(savedApod: SavedApod)

    @Query("SELECT * FROM $SAVED_APODS_TABLE_NAME WHERE date = :date")
    suspend fun isSavedByDate(date: String): SavedApod?
}