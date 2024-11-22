package com.example.gwweighscale.Repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.Rooms.Dao.TareDao
import com.example.gwweighscale.Rooms.Entities.Tare


class TareRepository(private val tareDao: TareDao) {

    // Fetch all Tares as LiveData
    val allTares: LiveData<List<Tare>> = tareDao.getAllTares()

    // Insert Tares into the database
    suspend fun insertTares(tares: List<Tare>) {
        tareDao.insertTares(tares)
    }
}
