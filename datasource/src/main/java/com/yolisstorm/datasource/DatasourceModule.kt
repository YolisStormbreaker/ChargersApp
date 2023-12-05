package com.yolisstorm.datasource

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datasourceModule = module {
    single<IRepository> { RepositoryImpl(androidContext().resources) }
}