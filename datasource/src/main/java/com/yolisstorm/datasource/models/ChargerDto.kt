package com.yolisstorm.datasource.models

import java.util.UUID

data class ChargerDto(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val city: String,
    val isBusy: Boolean,
    val address: String
)
