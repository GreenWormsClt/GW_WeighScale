package com.example.gwweighscale.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.gwweighscale.Repository.TareRepository
import com.example.gwweighscale.Rooms.Database.AppDatabase
import com.example.gwweighscale.Rooms.Entities.Tare
import kotlinx.coroutines.launch

class TareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TareRepository
    val allTares: LiveData<List<Tare>> // Expose Tare data as LiveData
    val isTrolleyPopupVisible = mutableStateOf(false)

    init {
        val tareDao = AppDatabase.getDatabase(application).tareDao()
        repository = TareRepository(tareDao)
        allTares = repository.allTares
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
}
