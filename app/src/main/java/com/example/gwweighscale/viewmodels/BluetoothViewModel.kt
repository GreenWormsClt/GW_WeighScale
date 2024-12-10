package com.example.gwweighscale.viewmodels

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val WEIGH_SCALE_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private var inputStream: BufferedReader? = null
    private var job: Job? = null
    private val appContext = application.applicationContext


    val status = mutableStateOf("Status: Disconnected")
    val weightsData = mutableStateOf("Waiting for data...")
    val netweight = mutableStateOf("00.00")
    val date = mutableStateOf("")
    val time = mutableStateOf("")
    private val macAddress = "00:04:3E:97:7C:61" // Replace with your device MAC address

    val permissionsGranted = mutableStateOf(false)

    // Bluetooth permissions for Android 12+ (API level 31 and above)
    private val bluetoothPermissions = arrayOf(
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN
    )

    // Check and request Bluetooth permissions
    fun checkBluetoothPermissions(activity: ComponentActivity, permissionsLauncher: ActivityResultLauncher<Array<String>>) {
        permissionsLauncher.launch(bluetoothPermissions)
    }

    fun updatePermissionsStatus(granted: Boolean) {
        permissionsGranted.value = granted
        if (!granted) {
            status.value = "Bluetooth permissions are required to use this feature"
        }
    }

    fun connect() {
        status.value = "Connecting..."

        // Check for Bluetooth permissions explicitly
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val appContext = getApplication<Application>().applicationContext
            val hasPermission = ContextCompat.checkSelfPermission(appContext, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                status.value = "Bluetooth permissions are required."
                return
            }
        }

        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.IO) {
                    val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(macAddress)
                    bluetoothSocket = device.createRfcommSocketToServiceRecord(WEIGH_SCALE_UUID)
                    bluetoothSocket!!.connect()
                    inputStream = BufferedReader(InputStreamReader(bluetoothSocket!!.inputStream))
                }

                status.value = "Connected"
                readData() // Call the function to read data from the device
            } catch (e: SecurityException) {
                status.value = "Permission denied: ${e.message}"
            } catch (e: Exception) {
                status.value = "Failed to connect: ${e.message}"
            }
        }
    }

    private suspend fun readData() {
        try {
            while (true) {
                val weight = inputStream?.readLine()
                if (weight != null) {
                    println("Weight read from device: $weight")
                    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    withContext(Dispatchers.Main) {
                        netweight.value = weight
                        date.value = currentDate
                        time.value = currentTime
                    }
                }
            }
        } catch (e: Exception) {
            println("Error reading data: ${e.message}")
        }
    }
}
