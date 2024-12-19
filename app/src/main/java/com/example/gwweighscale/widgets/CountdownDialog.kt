package com.example.gwweighscale.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CountdownDialog(
    onDismiss: () -> Unit,
    initialMinutes: Int,
    onTimeOut: () -> Unit
) {
    var totalTimeLeft by remember { mutableStateOf(initialMinutes * 60) } // Convert minutes to seconds


    LaunchedEffect(Unit) {
        while (totalTimeLeft > 0) {
            kotlinx.coroutines.delay(1000L) // 1 second delay
            totalTimeLeft--
        }
        onTimeOut() // Trigger the timeout callback when the countdown ends
    }
    val minutes = totalTimeLeft / 60
    val seconds = totalTimeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    androidx.compose.material.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Alert")
        },
        text = {
            Text(text = "Duplicate entry detected. Please try again after $formattedTime.")
        },
        confirmButton = {
            androidx.compose.material.Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
