package com.example.gwweighscale.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.gwweighscale.models.ItemModel
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.models.TareModel
import androidx.lifecycle.viewModelScope
import com.example.essaeweighingscale_2p00.EssaeWeighingScale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeighScaleViewModel : ViewModel() {

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
    private val _trolleyList = mutableStateOf(
        listOf(
            TareModel(id = "Trolley1", weight = 1600),
            TareModel(id = "Trolley2", weight = 1700),
            TareModel(id = "Trolley3", weight = 1800),
            TareModel(id = "Trolley1", weight = 1600),
            TareModel(id = "Trolley2", weight = 1700),
            TareModel(id = "Trolley3", weight = 1800)
        )
    )
    val trolleyList: State<List<TareModel>> = _trolleyList

    private val _rfidTag = MutableStateFlow("")
    val rfidTag: StateFlow<String> = _rfidTag


    // Function to handle RFID scanning
    fun onRfidScanned(tag: String, onNavigateToItemSelection: () -> Unit) {
        viewModelScope.launch {
            _rfidTag.emit(tag)
            if (tag.isNotEmpty()) {
                onNavigateToItemSelection()  // Navigate to item selection
            }
        }
    }

    fun updateRfidTag(newTag: String) {
        _rfidTag.value = newTag
    }


    // Function to handle Save button click
    fun onSaveClick() {
        //  onNavigateToItemSelection()

    }

    // Function to handle Tare button click
    fun onTareClick() {
        _isTrolleyPopupVisible.value = true
    }

    // Function to handle closing the trolley popup
    fun onTrolleyPopupClose() {
        _isTrolleyPopupVisible.value = false
    }

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
    //    // Function to connect to the Essae Scale via Bluetooth and get weight
//    fun getWeight(bluetoothAddress: String) {
//        viewModelScope.launch {
//            try {
//                // Connect to the Essae Scale via Bluetooth
//                val connectionResult = essaeScale.EssaeScale_MapSettings("BLUETOOTH", bluetoothAddress, 0)
//
//                if (connectionResult == "Connected") {
//                    // Successfully connected, now get the weight
//                    val weightResult = essaeScale.EssaeScale_GetWeight("BLUETOOTH", bluetoothAddress, 0)
//                    _weight.value = "$weightResult KG"
//                } else {
//                    // Handle failed connection
//                    _weight.value = "0.00"
//                }
//            } catch (e: Exception) {
//                // Handle any exceptions (e.g., connection issues or Bluetooth errors)
//                _weight.value = "Error: ${e.message}"
//            }
//        }
//    }


}
