package com.bustasirio.aries.feature_apod.domain.use_case

data class ApodUsesCases(
    val getApod: GetApod,
    val getApodList: GetApodList,
    val deleteSavedApod: DeleteSavedApod,
    val getSavedApods: GetSavedApods,
    val insertSavedApod: InsertSavedApod,
    val isSavedApod: IsSavedApod
)