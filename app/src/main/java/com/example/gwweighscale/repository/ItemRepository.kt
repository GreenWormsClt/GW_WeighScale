// File: Repository/ItemRepository.kt
package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.rooms.dao.ItemDao
import com.example.gwweighscale.rooms.entities.Item

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insertItem(item) // Insert a single item
    }

    suspend fun insertAll(items: List<Item>) {
        itemDao.insertItems(items)
    }

    suspend fun delete(item: Item) {
        itemDao.deleteItem(item)
    }
}
