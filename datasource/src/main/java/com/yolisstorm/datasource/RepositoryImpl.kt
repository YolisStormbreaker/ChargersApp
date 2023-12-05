package com.yolisstorm.datasource

import android.content.res.Resources
import com.yolisstorm.datasource.models.Charger
import com.yolisstorm.datasource.models.ChargerDto
import com.yolisstorm.datasource.models.CityWithCharger
import com.yolisstorm.datasource.models.DtoConverter.toChargerDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.serialization.json.Json
import java.util.UUID

class RepositoryImpl(
    private val resources: Resources
): IRepository {

    private val repoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val latestData: Flow<List<ChargerDto>> by lazy {
        resources
            .openRawResource(R.raw.charger_by_cities)
            .bufferedReader()
            .lineSequence()
            .map { rawString ->
                Json.decodeFromString<List<CityWithCharger>>(rawString)
                    .map { it.toChargerDto()  }
            }
            .asFlow()
            .shareIn(
                repoScope,
                replay = 1,
                started = SharingStarted.WhileSubscribed()
            )
    }


    override suspend fun getCitiesList(): Flow<List<String>> =
        latestData.map { chargerDtos -> chargerDtos.map { it.city }.distinct() }

    override suspend fun getChargersByCityName(
        cityName: String
    ): Flow<List<ChargerDto>> = latestData
        .map { chargerDtoList ->
            chargerDtoList
                .sortedBy {
                    it.name
                }
        }
}