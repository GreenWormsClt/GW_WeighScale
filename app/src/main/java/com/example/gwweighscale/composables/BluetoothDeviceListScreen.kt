package com.example.gwweighscale.composables

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.viewmodels.BluetoothViewModel
import com.example.gwweighscale.viewmodels.BluetoothtestViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothDeviceListScreen(
    bluetoothtestViewModel: BluetoothtestViewmodel = viewModel(),
    bluetoothViewModel: BluetoothViewModel,
    onBackClick: () -> Unit,
    onDeviceSelected: () -> Unit
) {
    val pairedDevices by bluetoothtestViewModel.pairedDevices.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        bluetoothtestViewModel.loadPairedDevices()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paired Bluetooth Devices", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )  { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (pairedDevices.isEmpty()) {
                Text(
                    text = "No Paired Devices Found",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(pairedDevices) { device ->
                        BluetoothDeviceItem(device = device,
                        onDeviceClick = {
                            bluetoothViewModel.setMacAddress(device.address)
                            bluetoothViewModel.connect()
                            onDeviceSelected() // Trigger navigation or other action
                        }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BluetoothDeviceItem(device: BluetoothDevice, onDeviceClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        .clickable { onDeviceClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Device Name: ${device.name ?: "Unknown"}", fontSize = 18.sp)
            Text(text = "MAC Address: ${device.address}", fontSize = 14.sp)
        }
    }
}
