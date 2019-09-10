package com.tetron.waybill

import android.app.Application
import com.google.firebase.FirebaseApp
import com.tetron.waybill.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@BaseApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(applicationModule)
        }
        FirebaseApp.initializeApp(this)
    }
}