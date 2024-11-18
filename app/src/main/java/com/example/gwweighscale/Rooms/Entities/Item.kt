package com.example.gwweighscale.Rooms.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val itemId: Int,
    val itemName: String
)
