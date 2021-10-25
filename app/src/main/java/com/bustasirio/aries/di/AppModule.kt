package com.bustasirio.aries.di

import com.bustasirio.aries.common.util.Constants.APOD_BASE_URL
import com.bustasirio.aries.feature_apod.data.remote.ApodApi
import com.bustasirio.aries.feature_apod.data.repository.ApodRepositoryImpl
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
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
    fun provideApodRepository(api: ApodApi): ApodRepository {
        return ApodRepositoryImpl(api)
    }
}