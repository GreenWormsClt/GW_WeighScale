
package com.example.gwweighscale.Rooms.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gwweighscale.Rooms.Entities.ItemReport

@Dao
interface ItemReportDao {
    @Query("SELECT * FROM item_reports")
    fun getAllReports(): LiveData<List<ItemReport>>

    @Insert
     fun insertReport(itemreport: ItemReport) // Ensure this is a suspend function

}
