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
                WeighScale(2, 2, 1, "GWASSET002"),
                WeighScale(3, 3, 1, "GWASSET003"),
                WeighScale(4, 3, 1, "GWASSET004"),
            )
            database.weighScaleDao().insertWeighScales(defaultWeighScales)

        }
    }
}
