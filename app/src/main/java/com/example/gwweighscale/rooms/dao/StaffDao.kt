package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.rooms.entities.Staff

@Dao
interface StaffDao {
    @Query("SELECT * FROM staff WHERE rfid = :rfid LIMIT 1")
    fun getStaffByRfid(rfid: String): LiveData<Staff?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(staff: Staff)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): LiveData<List<Staff>>

    @Delete
    suspend fun delete(staff: Staff) // Add delete method
}