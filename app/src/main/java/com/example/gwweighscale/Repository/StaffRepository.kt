package com.example.gwweighscale.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.gwweighscale.Rooms.Dao.StaffDao
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff


class StaffRepository(private val staffDao: StaffDao) {

    val allStaff: LiveData<List<Staff>> = staffDao.getAllStaff()

    suspend fun getStaffByRfid(rfid: String): Staff? = staffDao.getStaffByRfid(rfid)
}
