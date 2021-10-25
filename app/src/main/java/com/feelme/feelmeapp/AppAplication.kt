package com.feelme.feelmeapp

import android.app.Application
import com.feelme.feelmeapp.di.viewModelModule
import com.feelme.feelmeapp.firebase.UserProfile
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppAplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppAplication)
            modules(viewModelModule)
        }
    }
}