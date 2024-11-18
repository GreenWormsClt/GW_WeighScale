package com.example.gwweighscale.Rooms

import com.example.gwweighscale.Rooms.Database.AppDatabase
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff
//import com.example.gwweighscale.Rooms.Entities.Staff
//import com.example.gwweighscale.Rooms.Entities.Fruit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataInitializer {
    fun populateDatabase(database: AppDatabase) {
        val itemDao = database.itemDao()
        val staffDao = database.staffDao()

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

                )
            itemDao.insertItems(defaultItems)

            val defaultStaff = listOf(
                Staff(1, "VISMAYA C", "4260699411")
            )
            staffDao.insertAll(defaultStaff)
        }
    }
}
