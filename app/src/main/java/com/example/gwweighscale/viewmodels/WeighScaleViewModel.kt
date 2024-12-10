package com.example.gwweighscale.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gwweighscale.models.PopupData
//import com.example.gwweighscale.models.TareModel
import androidx.lifecycle.viewModelScope
import com.example.gwweighscale.repository.StaffRepository
import com.example.gwweighscale.repository.WeighScaleRepository
import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.rooms.entities.WeighScale
import kotlinx.coroutines.launch

class WeighScaleViewModel(application: Application) : AndroidViewModel(application) {
    private val weighScaleRepository: WeighScaleRepository


    val allWeighScales: LiveData<List<WeighScale>>

    init {
        val weighScaleDao = AppDatabase.getDatabase(application).weighScaleDao()
        weighScaleRepository = WeighScaleRepository(weighScaleDao)
        allWeighScales = weighScaleRepository.allWeighScales
    }


    fun insertWeighScale(weighScale: WeighScale) = viewModelScope.launch {
        weighScaleRepository.insertWeighScale(weighScale)
    }

    fun deleteWeighScale(weighScale: WeighScale) = viewModelScope.launch {
        weighScaleRepository.deleteWeighScale(weighScale)
    }
    fun getWeighScaleByCode(machineCode: String): WeighScale? {
        return allWeighScales.value?.find { it.machineCode == machineCode }
    }

    private val repository: StaffRepository
    val allStaffs: LiveData<List<Staff>>

    init {
        val staffDao = AppDatabase.getDatabase(application).staffDao()
        repository = StaffRepository(staffDao)
        allStaffs = repository.allStaff
    }

    private val _rfidMatch = MutableLiveData<Staff?>()
    val rfidMatch: LiveData<Staff?> = _rfidMatch

    fun validateRfidAndFetchStaffName(rfid: String): String? {
        val staff = allStaffs.value?.find { it.rfid == rfid }
        _rfidMatch.postValue(staff)
        return staff?.userName
    }
    fun validateRfidAndFetchStaffId(rfid: String): Int? {
        val staff = allStaffs.value?.find { it.rfid == rfid }
        _rfidMatch.postValue(staff) // Update LiveData for observers
        return staff?.id // Return the staff object
    }



    private val _calculatedWeight = MutableLiveData<Double>()
    val calculatedWeight: LiveData<Double> = _calculatedWeight

    fun updateCalculatedWeight(netWeight: Double, tareWeight: Double) {
        _calculatedWeight.value = netWeight - tareWeight
    }
    // MutableState for the weight
    private val _weight = mutableStateOf("0.00")
    val weight: State<String> = _weight

    // MutableState for popup visibility
    private val _isPopupVisible = mutableStateOf(false)
    val isPopupVisible: State<Boolean> = _isPopupVisible

    // MutableState for popup data
    private val _popupData = mutableStateOf<List<PopupData>>(emptyList())
    val popupData: State<List<PopupData>> = _popupData

    // MutableState for trolley popup visibility
    private val _isTrolleyPopupVisible = mutableStateOf(false)
    val isTrolleyPopupVisible: State<Boolean> = _isTrolleyPopupVisible


    // Function to handle View button click
    fun onViewClick() {
        fetchPopupData()
        _isPopupVisible.value = true
    }

    // Function to handle cross button click and hide the popup
    fun onPopupClose() {
        _isPopupVisible.value = false
    }

    // Function to fetch data for the popup
    private fun fetchPopupData() {
        _popupData.value = listOf(
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG"),
            PopupData("RAMU", "13/08/2024", "13:54:22", "1040.50 KG")

        )
    }



}
