package com.example.gwweighscale.rooms

import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.rooms.entities.Tare
import com.example.gwweighscale.rooms.entities.WeighScale
//import com.example.gwweighscale.Rooms.Entities.Staff
//import com.example.gwweighscale.Rooms.Entities.Fruit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataInitializer {
    fun populateDatabase(database: AppDatabase) {
        val itemDao = database.itemDao()
        val staffDao = database.staffDao()
        val tareDao = database.tareDao()

        CoroutineScope(Dispatchers.IO).launch {
            // Insert default items
            val defaultItems = listOf(
                Item(1, itemName = "LD"),
                Item(2, itemName = "Aluminum foil"),
                Item(3, itemName = "PP"),
                Item(4, itemName = "AFR"),
                Item(5, itemName = "HM"),
                Item(6, itemName = "BB"),
                Item(7, itemName = "BSK"),
                Item(8, itemName = "footwear"),
                Item(9, itemName = "rafiya"),
                Item(10, itemName = "LD"),
                Item(11, itemName = "Aluminum foil"),
                Item(12, itemName = "PP"),
                Item(13, itemName = "rafiya"),
                Item(14, itemName = "LD"),
                Item(15, itemName = "Aluminum foil"),
                Item(16, itemName = "PP"),
                Item(17, itemName = "PP"),
                Item(18, itemName = "rafiya"),
                Item(19, itemName = "LD"),
                Item(20, itemName = "Aluminum foil"),
                Item(21, itemName = "PP"),


                )
            itemDao.insertItems(defaultItems)

            val defaultStaff = listOf(
                Staff(1, "VISMAYA C", "4260699411"),
                Staff(2, "KISHORE", "1234567890"),
                Staff(3, "RAMU", "13455"),
            )
            staffDao.insertAll(defaultStaff)

            val defaultTares = listOf(
                Tare(1, "Trolley1", 0.123)

            )
            database.tareDao().insertTares(defaultTares)
            val defaultWeighScales = listOf(
                WeighScale(1, 1, 1, "GWASSET001"),
                WeighScale(2, 2, 2, "GWASSET002"),
                WeighScale(3, 3, 3, "GWASSET003"),
            )
            database.weighScaleDao().insertWeighScales(defaultWeighScales)


        }
    }
}
