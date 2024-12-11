package com.example.gwweighscale.viewmodels

import android.app.Application
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gwweighscale.utils.getPairedDevices

class BluetoothtestViewmodel(application: Application) : AndroidViewModel(application) {

    private val _pairedDevices = MutableLiveData<List<BluetoothDevice>>()
    val pairedDevices: LiveData<List<BluetoothDevice>> get() = _pairedDevices

    fun loadPairedDevices() {
        _pairedDevices.value = getPairedDevices()
    }


}