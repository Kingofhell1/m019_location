package com.example.m019_location.permission

import android.app.Application
import com.yandex.mapkit.MapKitFactory

private const val MAP_API_KEY = "708961b3-a564-4210-8126-6725033cc894"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAP_API_KEY)
    }
}