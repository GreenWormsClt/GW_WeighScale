package com.example.gwweighscale.Rooms.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff")
data class Staff(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val rfid: String
)
