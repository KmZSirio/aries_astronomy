package com.bustasirio.aries.feature_apod.data.remote

import com.bustasirio.aries.feature_apod.domain.model.Apod
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApodApi {

    @GET("apod")
    suspend fun getApodList(
        @Query("api_key") apiKey : String,
        @Query("start_date") startDate : String,
        @Query("end_date") endDate : String,
        @Query("thumbs") thumbs : Boolean
    ) : List<Apod>

    @GET("apod")
    suspend fun getApod(
        @Query("api_key") apiKey : String,
        @Query("date") date : String,
        @Query("thumbs") thumbs : Boolean
    ) : Apod
}