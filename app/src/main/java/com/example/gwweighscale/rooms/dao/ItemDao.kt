// File: Rooms/Dao/ItemDao.kt
package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gwweighscale.rooms.entities.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item) // Accepts a single item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Item>)

    @Delete
    suspend fun deleteItem(item: Item)
}
