package com.example.gwweighscale.rooms.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "item_reports",
    foreignKeys = [
        ForeignKey(
            entity = WeighScale::class,
            parentColumns = ["machineId"],
            childColumns = ["machineId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Staff::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Item::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["machineId"]),
        Index(value = ["userId"]),
        Index(value = ["itemId"])
    ]
)
data class ItemReport(
    @PrimaryKey(autoGenerate = true) val reportId: Int = 0,
    val mrfId: Int,
    val plantId: Int,
    val machineId: Int,
    val weight: Double,
    val userId: Int,
    val itemId: Int
)
