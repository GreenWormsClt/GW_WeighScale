package com.example.gwweighscale.rooms.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class Credential(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val machineCode: String,
    val password: String
)
