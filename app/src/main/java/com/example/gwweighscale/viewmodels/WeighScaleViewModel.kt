package com.example.gwweighscale.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gwweighscale.models.ItemModel
import com.example.gwweighscale.models.PopupData
//import com.example.gwweighscale.models.TareModel
import androidx.lifecycle.viewModelScope
import com.example.essaeweighingscale_2p00.EssaeWeighingScale
import com.example.gwweighscale.Repository.ItemRepository
import com.example.gwweighscale.Repository.StaffRepository
import com.example.gwweighscale.Repository.WeighScaleRepository
import com.example.gwweighscale.Rooms.Database.AppDatabase
import com.example.gwweighscale.Rooms.Entities.Item
import com.example.gwweighscale.Rooms.Entities.Staff
import com.example.gwweighscale.Rooms.Entities.Tare
import com.example.gwweighscale.Rooms.Entities.WeighScale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    // MutableState for trolley list
//    private val _trolleyList = mutableStateOf(
//        listOf(
//            TareModel(id = "Trolley1", weight = 1600),
//            TareModel(id = "Trolley2", weight = 1700),
//            TareModel(id = "Trolley3", weight = 1800),
//            TareModel(id = "Trolley1", weight = 1600),
//            TareModel(id = "Trolley2", weight = 1700),
//            TareModel(id = "Trolley3", weight = 1800)
//        )
//    )
  //  val trolleyList: State<List<TareModel>> = _trolleyList

    // Function to handle Save button click
    fun onSaveClick() {
       //  onNavigateToItemSelection()

    }

//    // Function to handle Tare button click
//    fun onTareClick() {
//        _isTrolleyPopupVisible.value = true
//    }
//
//    // Function to handle closing the trolley popup
//    fun onTrolleyPopupClose() {
//        _isTrolleyPopupVisible.value = false
//    }

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
