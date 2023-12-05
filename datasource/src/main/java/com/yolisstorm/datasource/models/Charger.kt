package com.yolisstorm.datasource.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Charger(
    @SerialName("name")
    val name: String,
    @SerialName("busy")
    val isBusy: Boolean,
    @SerialName("address")
    val address: String
)
