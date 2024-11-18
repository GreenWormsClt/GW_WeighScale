package com.example.gwweighscale

import android.app.Application
import com.example.gwweighscale.Rooms.DataInitializer
import com.example.gwweighscale.Rooms.Database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)

        // Populate the database with default data
        DataInitializer.populateDatabase(database)
    }
}
