package com.example.gwweighscale.Repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.Rooms.Dao.ItemReportDao
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.ItemReport

class ItemReportRepository(private val itemReportDao: ItemReportDao) {

    val allReports: LiveData<List<ItemReport>> = itemReportDao.getAllReports()

    fun insertReport(itemreport: ItemReport) {
        itemReportDao.insertReport(itemreport) // This now correctly calls the suspend DAO method
    }

}

