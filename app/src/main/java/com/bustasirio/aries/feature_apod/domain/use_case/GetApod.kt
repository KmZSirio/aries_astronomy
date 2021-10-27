package com.bustasirio.aries.feature_apod.domain.use_case

import android.util.Log
import com.bustasirio.aries.common.util.Resource
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetApod @Inject constructor(
    private val repository: ApodRepository
) {

    operator fun invoke(
        apiKey: String,
        date: String,
        thumbs: Boolean
    ): Flow<Resource<Apod>> = flow {
        try {
            emit(Resource.Loading())
            val apod = repository.getApod(apiKey, date, thumbs)
            emit(Resource.Success(apod))
        } catch (e: HttpException) {
            emit(Resource.Error<Apod>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<Apod>("Couldn't reach server. Check your internet connection."))
        }
    }
}