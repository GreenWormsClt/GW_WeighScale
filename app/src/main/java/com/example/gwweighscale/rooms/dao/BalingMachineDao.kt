package com.example.gwweighscale.rooms.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gwweighscale.rooms.entities.WeighScale

@Dao
interface WeighScaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeighScale(weighScale: WeighScale)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeighScales(weighScales: List<WeighScale>)

    @Query("SELECT * FROM weigh_scale")
    fun getAllWeighScales(): LiveData<List<WeighScale>>

    @Delete
    suspend fun deleteWeighScale(weighScale: WeighScale)
}

