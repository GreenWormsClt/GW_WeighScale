package com.example.gwweighscale.rooms

import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.rooms.entities.Tare
import com.example.gwweighscale.rooms.entities.WeighScale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataInitializer {
    fun populateDatabase(database: AppDatabase) {

        CoroutineScope(Dispatchers.IO).launch {

            val defaultWeighScales = listOf(
                WeighScale(1, 1, 1, "GWASSET001"),
                WeighScale(2, 2, 2, "GWASSET002"),
                WeighScale(3, 3, 3, "GWASSET003"),
            )
            database.weighScaleDao().insertWeighScales(defaultWeighScales)

        }
    }
}
