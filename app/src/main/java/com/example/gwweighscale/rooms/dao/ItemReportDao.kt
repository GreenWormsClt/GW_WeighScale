
package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.rooms.entities.ItemReport

@Dao
interface ItemReportDao {
    @Query("SELECT * FROM item_reports")
    fun getAllReports(): LiveData<List<ItemReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(itemreport: ItemReport) // Ensure this is a suspend function

    @Query("""
    SELECT ir.reportId, s.userName AS staffName, i.itemName, ir.weight, ir.date, ir.time
    FROM item_reports AS ir
    INNER JOIN staff AS s ON ir.userId = s.id
    INNER JOIN items AS i ON ir.itemId = i.itemId
""")
    fun getReportDetails(): LiveData<List<PopupData>>
}

