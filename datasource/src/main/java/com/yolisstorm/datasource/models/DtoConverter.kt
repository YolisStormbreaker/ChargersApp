package com.yolisstorm.datasource.models

object DtoConverter {

    fun CityWithCharger.toChargerDto() = ChargerDto(
        name = charger.name,
        city = this.name,
        isBusy = charger.isBusy,
        address = charger.address
    )

}