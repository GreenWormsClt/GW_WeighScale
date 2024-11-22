package com.example.gwweighscale.Rooms.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gwweighscale.Rooms.Dao.ItemDao
import com.example.gwweighscale.Rooms.Dao.ItemReportDao
import com.example.gwweighscale.Rooms.Dao.StaffDao
import com.example.gwweighscale.Rooms.Dao.TareDao
import com.example.gwweighscale.Rooms.Dao.WeighScaleDao
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.ItemReport
import com.example.gwweighscale.Rooms.Entities.Staff
import com.example.gwweighscale.Rooms.Entities.Tare
import com.example.gwweighscale.Rooms.Entities.WeighScale

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
