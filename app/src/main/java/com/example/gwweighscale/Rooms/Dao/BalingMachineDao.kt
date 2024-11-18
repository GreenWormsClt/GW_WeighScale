//package com.example.gwweighscale.Rooms.Dao
//
//import androidx.room.*
//import com.example.gwweighscale.Rooms.Entities.BalingMachine
//
//@Dao
//interface BalingMachineDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(balingMachine: BalingMachine)
//
//    @Query("SELECT * FROM baling_machines")
//    suspend fun getAllBalingMachines(): List<BalingMachine>
//
//    @Query("SELECT * FROM baling_machines WHERE machineId = :id")
//    suspend fun getBalingMachineById(id: Int): BalingMachine?
//
//    @Delete
//    suspend fun deleteBalingMachine(balingMachine: BalingMachine)
//}
