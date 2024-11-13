package com.example.gwweighscale.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.gwweighscale.models.ItemModel
import com.example.gwweighscale.composables.ItemSelectionScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ItemSelectionViewModel : ViewModel() {

    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String> = _selectedItem

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _staffName = MutableLiveData("RAMU")
    val staffName: LiveData<String> = _staffName

    private val _weight = MutableLiveData("1040")
    val weight: LiveData<String> = _weight

    private val _shouldShowTextFieldAndButton = MutableStateFlow(true)
    val shouldShowTextFieldAndButton: StateFlow<Boolean> = _shouldShowTextFieldAndButton

    fun setShouldShowTextFieldAndButton(show: Boolean) {
        _shouldShowTextFieldAndButton.value = show
    }
    fun getItemRows(): List<List<String>> {
        // Returns a list of rows, where each row is a list of item names
        return listOf(
            listOf("LD", "Aluminum foil", "PP", "AFR", "HM"),
            listOf("HM", "LD", "BB", "BSK", "HM"),
            listOf("LD", "HM", "footwear", "AFR", "HM"),
            listOf("HM", "LD", "rafiya", "BSK", "rafiya"),

            )
    }

    fun selectItem(item: ItemModel) {
        _selectedItem.value = item.name
        _toastMessage.value = "Item ${item.name} added successfully!"
    }
}
