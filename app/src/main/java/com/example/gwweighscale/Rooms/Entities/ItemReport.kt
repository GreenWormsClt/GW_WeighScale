package com.example.gwweighscale.Rooms.Entities

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
            parentColumns = ["id"], // Corrected to match the primary key of the Staff table
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Item::class,
            parentColumns = ["itemId"], // Ensure the Item table has itemId as its primary key
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
    @PrimaryKey(autoGenerate = true) val reportId: Int = 0, // Enable auto-generation for unique IDs
    val mrfId: Int, // MRF identifier
    val plantId: Int, // Plant identifier
    val machineId: Int, // Foreign key to WeighScale
    val weight: Double, // Weight value
    val userId: Int, // Foreign key to Staff
    val itemId: Int // Foreign key to Item
)
