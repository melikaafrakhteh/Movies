package com.afrakhteh.movies

import android.app.Application
import com.afrakhteh.movies.di.module.appModule
import com.afrakhteh.movies.di.module.repositoryModule
import com.afrakhteh.movies.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CalligraphyConfig.initDefault(
                CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/opensans.ttf")
                    .setFontAttrId(R.attr.font)
                    .build()
            )
        }

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }

    }


}
