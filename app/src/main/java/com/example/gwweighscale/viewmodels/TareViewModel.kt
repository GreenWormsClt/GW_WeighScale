package com.example.gwweighscale.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.gwweighscale.models.TareModel

class TareViewModel : ViewModel() {
    val isTrolleyPopupVisible = mutableStateOf(false)
    val trolleyList = mutableStateListOf(
        TareModel(id = "Trolley1", weight = 1600),
        TareModel(id = "Trolley2", weight = 1700),
        TareModel(id = "Trolley3", weight = 1800),
        TareModel(id = "Trolley1", weight = 1600),
        TareModel(id = "Trolley2", weight = 1700),
    )

    fun onTareClick() {
        isTrolleyPopupVisible.value = true
    }

    fun onTrolleyPopupClose() {
        isTrolleyPopupVisible.value = false
    }
}
