package com.yolisstorm.datasource.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CityWithCharger(
    @SerialName("city")
    val name: String,
    @SerialName("charger")
    val charger: Charger
)
