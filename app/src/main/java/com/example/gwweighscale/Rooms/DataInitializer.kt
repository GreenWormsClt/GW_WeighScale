package com.example.gwweighscale.Rooms

import com.example.gwweighscale.Rooms.Database.AppDatabase
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff
import com.example.gwweighscale.Rooms.Entities.Tare
import com.example.gwweighscale.Rooms.Entities.WeighScale
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
//                Item(14, itemName = "LD"),
//                Item(15, itemName = "Aluminum foil"),
//                Item(16, itemName = "PP"),


                )
            itemDao.insertItems(defaultItems)

            val defaultStaff = listOf(
                Staff(1, "VISMAYA C", "4260699411"),
                Staff(2, "KISHORE", "12345")
            )
            staffDao.insertAll(defaultStaff)

            val defaultTares = listOf(
                Tare(1, "Trolley1", 0.123),
                Tare(2, "Trolley2", 100.00),
                Tare(3, "Trolley3", 60.00),
                Tare(4, "Trolley4", 240.00),
                Tare(5, "Trolley5", 300.00),
                Tare(6, "Trolley6", 148.00),
                Tare(7, "Trolley7", 210.00),
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
