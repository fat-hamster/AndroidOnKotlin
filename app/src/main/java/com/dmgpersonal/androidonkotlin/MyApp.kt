package com.dmgpersonal.androidonkotlin

import android.app.Application

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        privateContext = this
    }

    companion object {
        private var privateContext: MyApp? = null
        val appContext get() = privateContext!!
    }
}