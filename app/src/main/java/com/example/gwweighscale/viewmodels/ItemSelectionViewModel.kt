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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gwweighscale.Repository.ItemRepository
import com.example.gwweighscale.Rooms.Database.AppDatabase
import com.example.gwweighscale.Rooms.Entities.Item
import kotlinx.coroutines.launch


class ItemSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository

    val allItems: LiveData<List<Item>>

    init {
        // Initialize database and repository
        val itemDao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        allItems = repository.allItems
    }

    fun insert(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }

    fun insertAll(items: List<Item>) = viewModelScope.launch {
        repository.insertAll(items)
    }

    fun addItem(itemId:Int ,itemName: String) = viewModelScope.launch {
        val newItem = Item(itemId = itemId, itemName = itemName) // Create a new item
        repository.insert(newItem) // Add to the database
    }

    fun delete(item: Item) = viewModelScope.launch {
        repository.delete(item)
    }
}

