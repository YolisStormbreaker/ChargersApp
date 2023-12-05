package com.yolisstorm.chargerslist

import com.yolisstorm.chargerslist.view.chargersView.ChargersListViewModel
import com.yolisstorm.chargerslist.view.citiesView.CitiesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    viewModel { CitiesListViewModel(get()) }
    viewModel { ChargersListViewModel(get()) }
}