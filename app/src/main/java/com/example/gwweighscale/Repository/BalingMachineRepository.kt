//package com.example.gwweighscale.Repository
//
//import com.example.gwweighscale.Rooms.Dao.BalingMachineDao
//import com.example.gwweighscale.Rooms.Entities.BalingMachine
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class BalingMachineRepository(private val dao: BalingMachineDao) {
//
//    suspend fun insertBalingMachine(machine: BalingMachine) {
//        withContext(Dispatchers.IO) {
//            dao.insert(machine)
//        }
//    }
//
//    suspend fun getAllMachines(): List<BalingMachine> {
//        return withContext(Dispatchers.IO) {
//            dao.getAllBalingMachines()
//        }
//    }
//
//    suspend fun getMachineById(id: Int): BalingMachine? {
//        return withContext(Dispatchers.IO) {
//            dao.getBalingMachineById(id)
//        }
//    }
//
//    suspend fun deleteMachine(machine: BalingMachine) {
//        withContext(Dispatchers.IO) {
//            dao.deleteBalingMachine(machine)
//        }
//    }
//}
