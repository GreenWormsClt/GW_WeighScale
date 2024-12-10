package com.example.gwweighscale.rooms.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gwweighscale.rooms.dao.ItemDao
import com.example.gwweighscale.rooms.dao.ItemReportDao
import com.example.gwweighscale.rooms.dao.StaffDao
import com.example.gwweighscale.rooms.dao.TareDao
import com.example.gwweighscale.rooms.dao.WeighScaleDao
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.rooms.entities.ItemReport
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.rooms.entities.Tare
import com.example.gwweighscale.rooms.entities.WeighScale

@Database(entities = [Item::class, Staff::class, Tare::class, WeighScale::class, ItemReport::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun staffDao(): StaffDao
    abstract fun tareDao(): TareDao
    abstract fun weighScaleDao(): WeighScaleDao
    abstract fun itemReportDao(): ItemReportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
