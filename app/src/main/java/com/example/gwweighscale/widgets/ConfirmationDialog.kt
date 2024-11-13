package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmationDialog(
    selectedItem: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmation") },
        text = { Text("Are you sure you want to select $selectedItem?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF026163)), // Set button color directly
                modifier = Modifier
                    .width(100.dp) // Set custom width for rectangle
                    .height(40.dp)
            ) {
                Text("Confirm", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF026163)), // Set button color directly
                modifier = Modifier
                    .width(100.dp) // Set custom width for rectangle
                    .height(40.dp)
            ) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}
