package com.example.gwweighscale.Rooms.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tares")
data class Tare(
    @PrimaryKey val tareId: Int,
    val name: String,
    val weight: Double
)