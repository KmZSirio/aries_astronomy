package com.bustasirio.aries.feature_apod.domain.use_case

import com.bustasirio.aries.common.util.Resource
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetApodList @Inject constructor(
    private val repository: ApodRepository
) {

    operator fun invoke(
        apiKey: String,
        startDate: String,
        endDate: String,
        thumbs: Boolean
    ): Flow<Resource<List<Apod>>> = flow {
        try {
            emit(Resource.Loading())
            val apods = repository.getApodList(apiKey, startDate, endDate, thumbs)
            emit(Resource.Success(apods))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Apod>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Apod>>("Couldn't reach server. Check your internet connection."))
        }
    }
}