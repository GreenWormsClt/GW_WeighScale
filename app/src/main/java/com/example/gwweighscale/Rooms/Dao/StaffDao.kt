package com.example.gwweighscale.Rooms.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff

@Dao
interface StaffDao {
    @Query("SELECT * FROM staff WHERE rfid = :rfid LIMIT 1")
     fun getStaffByRfid(rfid: String): Staff?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(staff: List<Staff>)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): LiveData<List<Staff>>
}