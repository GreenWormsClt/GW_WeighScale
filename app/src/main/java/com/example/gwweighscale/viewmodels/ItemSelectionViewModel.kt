/*package com.example.gwweighscale.viewmodels

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
*/
package com.example.gwweighscale.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.gwweighscale.repository.ItemReportRepository
import com.example.gwweighscale.repository.ItemRepository
import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.rooms.entities.ItemReport
import kotlinx.coroutines.launch


class ItemSelectionViewModel(application: Application) : AndroidViewModel(application) {
    private val reportRepository: ItemReportRepository
    val allReports: LiveData<List<ItemReport>>

    init {
        val reportDao = AppDatabase.getDatabase(application).itemReportDao()
        reportRepository = ItemReportRepository(reportDao)
        allReports = reportRepository.allReports
    }

    fun insertReport(
        mrfId: Int,
        plantId: Int,
        machineId: Int,
        weight: Double,
        userId: Int,
        itemId: Int,
        date: String,
        time: String,
        onDuplicate: () -> Unit,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val isDuplicate = reportRepository.isDuplicateReport(
                itemId = itemId,
                userId = userId,
                weight = weight,
                date = date,
                time = time
            )
            if (isDuplicate) {
                onDuplicate()
            } else {
                val newReport = ItemReport(
                    mrfId = mrfId,
                    plantId = plantId,
                    machineId = machineId,
                    weight = weight,
                    userId = userId,
                    itemId = itemId,
                    date = date,
                    time = time
                )
                reportRepository.insertReport(newReport)
                onSuccess()
            }
        }
    }

    private val repository: ItemRepository

    val allItems: LiveData<List<Item>>

    init {
        val itemDao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        allItems = repository.allItems
    }

    fun insertItem(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}
