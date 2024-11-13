package com.example.gwweighscale.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.gwweighscale.models.PopupData

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


    // Function to handle Save button click
    fun onSaveClick() {
        // Implement save logic here
    }

    // Function to handle Tare button click
    fun onTareClick() {
        // Implement tare logic here
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

    // Function to update weight (example function)
    fun updateWeight(newWeight: String) {
        _weight.value = "$newWeight KG"
    }
}