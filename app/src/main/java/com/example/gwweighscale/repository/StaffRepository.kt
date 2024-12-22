package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.rooms.dao.StaffDao
import com.example.gwweighscale.rooms.entities.Staff

class StaffRepository(private val staffDao: StaffDao) {

    val allStaff: LiveData<List<Staff>> = staffDao.getAllStaff()

    fun getStaffByRfid(rfid: String): LiveData<Staff?> = staffDao.getStaffByRfid(rfid)

    suspend fun insert(staff: Staff) {
        staffDao.insert(staff)
    }

    suspend fun delete(staff: Staff) {
        staffDao.delete(staff)
    }
}
