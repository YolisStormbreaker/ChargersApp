package com.yolisstorm.chargerslist.view.chargersView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yolisstorm.datasource.IRepository
import com.yolisstorm.datasource.models.ChargerDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChargersListViewModel (
    private val repository: IRepository
): ViewModel() {

    private val _chargersList = MutableStateFlow<List<ChargerDto>>(emptyList())
    val chargersList = _chargersList.asStateFlow()

    fun updateChargersList(cityName: String) {
        viewModelScope.launch {
            repository
                .getChargersByCityName(cityName)
                .collect { list ->
                    _chargersList.value = list
                }
        }
    }

}