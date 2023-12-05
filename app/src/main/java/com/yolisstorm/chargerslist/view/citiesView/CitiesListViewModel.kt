package com.yolisstorm.chargerslist.view.citiesView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yolisstorm.datasource.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitiesListViewModel (
    private val repository: IRepository
): ViewModel() {

    private val _citiesList = MutableStateFlow<List<CityNameValueClass>>(emptyList())
    val citiesList = _citiesList.asStateFlow()

    fun updateCitiesList() {
        viewModelScope.launch {
            repository
                .getCitiesList()
                .collect { list ->
                    _citiesList.value = list.map { CityNameValueClass(it) }
                }
        }
    }

}