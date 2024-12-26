package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.rooms.dao.TareDao
import com.example.gwweighscale.rooms.entities.Tare


class TareRepository(private val tareDao: TareDao) {

    // Fetch all Tares as LiveData
    val allTares: LiveData<List<Tare>> = tareDao.getAllTares()

    // Insert Tares into the database
    suspend fun insertTares(tares: List<Tare>) {
        tareDao.insertTares(tares)
    }
    suspend fun getMaxTareId(): Int? {
        return tareDao.getMaxTareId()
    }

    suspend fun deleteTare(tare: Tare) {
        tareDao.deleteTare(tare)
    }

}
