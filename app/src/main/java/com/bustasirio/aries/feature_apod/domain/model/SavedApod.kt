package com.bustasirio.aries.feature_apod.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bustasirio.aries.common.util.Constants.SAVED_APODS_TABLE_NAME

@Entity(tableName = SAVED_APODS_TABLE_NAME)
data class SavedApod(
    @Embedded
    val apod: Apod,
    val timestamp: Long,
    @PrimaryKey val id: String
)
