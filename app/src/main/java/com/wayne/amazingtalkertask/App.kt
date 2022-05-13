package com.wayne.amazingtalkertask

import android.app.Application
import com.wayne.amazingtalkertask.di.networkModule
import com.wayne.amazingtalkertask.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule)
        }
    }
}
