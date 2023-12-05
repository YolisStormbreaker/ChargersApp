package com.yolisstorm.chargerslist

import android.app.Application
import com.yolisstorm.datasource.datasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                applicationModule,
                datasourceModule
            )
        }
    }
}