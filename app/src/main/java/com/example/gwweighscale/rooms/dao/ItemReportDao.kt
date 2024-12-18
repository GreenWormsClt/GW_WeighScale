
package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.models.ReportData
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
    // Count similar reports within 5 minutes
    @Query("""
        SELECT COUNT(*) 
        FROM item_reports 
        WHERE itemId = :itemId 
          AND userId = :userId 
          AND weight = :weight 
          AND date = :date 
          AND ABS(strftime('%s', time) - strftime('%s', :time)) <= 300
    """)
    suspend fun countSimilarReports(
        itemId: Int,
        userId: Int,
        weight: Double,
        date: String,
        time: String
    ): Int

    @Query("""
    SELECT 
        ir.reportId, 
        s.userName AS staffName, 
        i.itemName, 
        SUM(ir.weight) AS totalWeight, 
        ir.date, 
        ir.time
    FROM item_reports AS ir
    INNER JOIN staff AS s ON ir.userId = s.id
    INNER JOIN items AS i ON ir.itemId = i.itemId
    GROUP BY s.userName, i.itemName, ir.date
""")
    fun getSummaryDetails(): LiveData<List<ReportData>>

    @Query("SELECT * FROM item_reports")
    suspend fun getAllReportsSync(): List<ItemReport>

}

