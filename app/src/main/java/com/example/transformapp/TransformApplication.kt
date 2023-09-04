package com.example.transformapp

import android.app.Application
import com.example.transformapp.di.repositoryModule
import com.example.transformapp.di.viewModelModule
import com.google.firebase.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TransformApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TransformApplication)
            androidLogger(
                if(BuildConfig.DEBUG)
                    Level.ERROR
                else
                    Level.NONE
            )
            modules(repositoryModule, viewModelModule)
        }
    }

}