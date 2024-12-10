package com.example.gwweighscale.rooms.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weigh_scale")
data class WeighScale(
    @PrimaryKey(autoGenerate = true) val machineId: Int = 0,
    val plantId: Int,
    val mrfId: Int,
    val machineCode: String
)

