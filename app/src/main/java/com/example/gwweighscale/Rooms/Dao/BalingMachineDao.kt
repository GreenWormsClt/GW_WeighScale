package com.example.gwweighscale.Rooms.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff
import com.example.gwweighscale.Rooms.Entities.WeighScale

@Dao
interface WeighScaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertWeighScale(weighScale: WeighScale)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insertWeighScales(weighScales: List<WeighScale>)

    @Query("SELECT * FROM weigh_scale")
    fun getAllWeighScales(): LiveData<List<WeighScale>>

    @Delete
    fun deleteWeighScale(weighScale: WeighScale)
}

