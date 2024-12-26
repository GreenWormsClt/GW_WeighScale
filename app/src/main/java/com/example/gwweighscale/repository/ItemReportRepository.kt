package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.models.ReportData
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


    suspend fun isDuplicateReport(
        itemId: Int,
        userId: Int,
        weight: Double,
        date: String,
        time: String
    ): Boolean {
        return itemReportDao.countSimilarReports(itemId, userId, weight, date, time) > 0
    }
    fun getSummaryDetails(): LiveData<List<ReportData>> {
        return itemReportDao.getSummaryDetails()
    }
    suspend fun getAllReportsSync(): List<ItemReport> {
        return itemReportDao.getAllReportsSync() // Create this DAO method
    }

    suspend fun resetReports() {
        itemReportDao.resetReports()
    }
}

