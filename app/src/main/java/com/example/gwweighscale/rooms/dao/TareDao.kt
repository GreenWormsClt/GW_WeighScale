package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.rooms.entities.ItemReport
import com.example.gwweighscale.rooms.entities.Tare

@Dao
interface TareDao {

    @Query("SELECT * FROM tares")
    fun getAllTares(): LiveData<List<Tare>> // Fetch all Tares as LiveData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTares(tares: List<Tare>) // Insert a list of Tares

    @Query("SELECT MAX(tareId) FROM tares")
    suspend fun getMaxTareId(): Int?

    @Delete
    suspend fun deleteTare(tare: Tare) // Delete a specific trolley

    @Query("SELECT * FROM tares WHERE tareId = :tareId LIMIT 1")
    suspend fun getTareById(tareId: Int): Tare?
}
