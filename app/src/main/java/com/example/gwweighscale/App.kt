package com.example.gwweighscale

import android.app.Application
import com.example.gwweighscale.rooms.DataInitializer
import com.example.gwweighscale.rooms.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)

        // Populate the database with default data
        DataInitializer.populateDatabase(database)
    }
}
