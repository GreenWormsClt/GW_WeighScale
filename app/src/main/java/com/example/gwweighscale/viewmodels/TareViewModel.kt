package com.example.gwweighscale.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gwweighscale.repository.TareRepository
import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.rooms.entities.Tare
import kotlinx.coroutines.launch

class TareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TareRepository
    val allTares: LiveData<List<Tare>> // Expose Tare data as LiveData
    val isTrolleyPopupVisible = mutableStateOf(false)
    var selectedTrolley = mutableStateOf<Tare?>(null)

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("app_prefs", Application.MODE_PRIVATE)
    init {
        val tareDao = AppDatabase.getDatabase(application).tareDao()
        repository = TareRepository(tareDao)
        allTares = repository.allTares
        loadSelectedTrolley()
    }


    fun onTareClick() {
        isTrolleyPopupVisible.value = true
    }

    fun onTrolleyPopupClose() {
        isTrolleyPopupVisible.value = false
    }

    // Insert default data (optional)
    fun insertDefaultTares(defaultTares: List<Tare>) {
        viewModelScope.launch {
            repository.insertTares(defaultTares)
        }
    }
    fun selectTrolley(tare: Tare) {
        viewModelScope.launch {
            repository.insertTares(listOf(tare))
            saveSelectedTrolley(tare.tareId)
            selectedTrolley.value = tare
        }
    }
    // Insert a single Tare
    fun insertTare(tare: Tare) {
        viewModelScope.launch {
            val maxTareId = repository.getMaxTareId() ?: 0 // Get the highest `tareId`
            val newTare = tare.copy(tareId = maxTareId + 1) // Assign the next available ID
            repository.insertTares(listOf(newTare))
        }
    }
    private fun saveSelectedTrolley(tareId: Int) {
        sharedPreferences.edit().putInt("selected_trolley_id", tareId).apply()
    }

    private fun loadSelectedTrolley() {
        viewModelScope.launch {
            val tareId = sharedPreferences.getInt("selected_trolley_id", -1)
            if (tareId != -1) {
                selectedTrolley.value = repository.getTareById(tareId)
            }
        }
    }

    fun deleteTare(tare: Tare) {
        viewModelScope.launch {
            repository.deleteTare(tare)
        }
    }

}
