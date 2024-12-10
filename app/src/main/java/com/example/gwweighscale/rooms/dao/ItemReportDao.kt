
package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gwweighscale.rooms.entities.ItemReport

@Dao
interface ItemReportDao {
    @Query("SELECT * FROM item_reports")
    fun getAllReports(): LiveData<List<ItemReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(itemreport: ItemReport) // Ensure this is a suspend function

}
