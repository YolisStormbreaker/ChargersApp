package com.yolisstorm.datasource

import com.yolisstorm.datasource.models.Charger
import com.yolisstorm.datasource.models.ChargerDto
import com.yolisstorm.datasource.models.CityWithCharger
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IRepository {

    suspend fun getCitiesList(): Flow<List<String>>

    suspend fun getChargersByCityName(cityName: String): Flow<List<ChargerDto>>

}