package com.example.gwweighscale.Repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.Rooms.Dao.ItemDao
import com.example.gwweighscale.Rooms.Entities.Item
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun insertAll(items: List<Item>) {
        itemDao.insertItems(items)
    }

    suspend fun delete(item: Item) {
        itemDao.deleteItem(item)
    }
}

