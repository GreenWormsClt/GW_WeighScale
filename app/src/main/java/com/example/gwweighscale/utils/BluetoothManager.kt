package com.example.gwweighscale.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

@SuppressLint("MissingPermission")
fun getPairedDevices(): List<BluetoothDevice> {
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    return bluetoothAdapter?.bondedDevices?.toList() ?: emptyList()
}
