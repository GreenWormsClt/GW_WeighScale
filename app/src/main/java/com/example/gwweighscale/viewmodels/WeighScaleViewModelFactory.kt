//
//package com.example.gwweighscale.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.gwweighscale.Repository.StaffRepository
//
//class WeighScaleViewModelFactory(
//    private val repository: StaffRepository
//) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(WeighScaleViewModel::class.java)) {
//            return WeighScaleViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}