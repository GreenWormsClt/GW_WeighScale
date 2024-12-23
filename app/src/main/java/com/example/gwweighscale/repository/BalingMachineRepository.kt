package com.example.gwweighscale.repository

import androidx.lifecycle.LiveData
import com.example.gwweighscale.rooms.dao.WeighScaleDao
import com.example.gwweighscale.rooms.entities.WeighScale


class WeighScaleRepository(private val weighScaleDao: WeighScaleDao) {

    val allWeighScales: LiveData<List<WeighScale>> = weighScaleDao.getAllWeighScales()

    suspend fun insertWeighScale(weighScale: WeighScale) {
        weighScaleDao.insertWeighScale(weighScale)
    }

    suspend fun insertWeighScales(weighScales: List<WeighScale>) {
        weighScaleDao.insertWeighScales(weighScales)
    }
    fun getWeighScaleByCode(machineCode: String): WeighScale? {
        return weighScaleDao.getWeighScaleByCode(machineCode)
    }

    suspend fun deleteWeighScale(weighScale: WeighScale) {
        weighScaleDao.deleteWeighScale(weighScale)
    }
}
