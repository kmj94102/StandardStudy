package com.example.standardstudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StandardStudy : Application() {

    companion object {
        private lateinit var application : StandardStudy
        fun getInstance() : StandardStudy = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}