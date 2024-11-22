package com.example.gwweighscale.Rooms.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Tare

@Dao
interface TareDao {

    @Query("SELECT * FROM tares")
    fun getAllTares(): LiveData<List<Tare>> // Fetch all Tares as LiveData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTares(tares: List<Tare>) // Insert a list of Tares
}
