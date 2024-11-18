// File: Rooms/Dao/ItemDao.kt
package com.example.gwweighscale.Rooms.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gwweighscale.Rooms.Entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<Item>)

    @Delete
    fun deleteItem(item: Item)
}
