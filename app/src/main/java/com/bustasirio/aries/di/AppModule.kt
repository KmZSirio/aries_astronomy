package com.bustasirio.aries.di

import android.app.Application
import androidx.room.Room
import com.bustasirio.aries.common.util.Constants.APOD_BASE_URL
import com.bustasirio.aries.feature_apod.data.local.ApodDao
import com.bustasirio.aries.feature_apod.data.local.ApodDatabase
import com.bustasirio.aries.feature_apod.data.remote.ApodApi
import com.bustasirio.aries.feature_apod.data.repository.ApodRepositoryImpl
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import com.bustasirio.aries.feature_apod.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApodApi(): ApodApi {
        return Retrofit.Builder()
            .baseUrl(APOD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApodApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApodDatabase(app: Application): ApodDatabase {
        return Room.databaseBuilder(
            app,
            ApodDatabase::class.java,
            ApodDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideApodRepository(api: ApodApi, db: ApodDatabase): ApodRepository {
        return ApodRepositoryImpl(api, db.apodDao)
    }

    @Singleton
    @Provides
    fun provideApodUseCases(repository: ApodRepository): ApodUsesCases {
        return ApodUsesCases(
            getApod = GetApod(repository),
            getApodList = GetApodList(repository),
            deleteSavedApod = DeleteSavedApod(repository),
            getSavedApods = GetSavedApods(repository),
            insertSavedApod = InsertSavedApod(repository),
            isSavedApod = IsSavedApod(repository)
        )
    }
}