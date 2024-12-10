package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.rooms.dao.ItemReportDao
import com.example.gwweighscale.rooms.entities.ItemReport

class ItemReportRepository(private val itemReportDao: ItemReportDao) {

    val allReports: LiveData<List<ItemReport>> = itemReportDao.getAllReports()

    suspend fun insertReport(itemreport: ItemReport) {
        itemReportDao.insertReport(itemreport) // This now correctly calls the suspend DAO method
    }
    fun getReportDetails(): LiveData<List<PopupData>> {
        return itemReportDao.getReportDetails()
    }

}

