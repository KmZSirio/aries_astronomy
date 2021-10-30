package com.bustasirio.aries.feature_apod.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bustasirio.aries.feature_apod.domain.model.SavedApod

@Database(
    entities = [SavedApod::class],
    version = 1
)
abstract class ApodDatabase: RoomDatabase() {
    abstract val apodDao: ApodDao

    companion object {
        const val DATABASE_NAME = "apod_db"
    }
}