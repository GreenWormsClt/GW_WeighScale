package com.example.gwweighscale.widgets


import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gwweighscale.rooms.entities.Staff

@Composable
fun AddStaffDialog(
    onDismiss: () -> Unit,
    onSave: (Staff) -> Unit
) {
    var staffName by remember { mutableStateOf("") }
    var rfid by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Staff") },
        text = {
            Column {
                TextField(
                    value = staffName,
                    onValueChange = { staffName = it },
                    label = { Text("Staff Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = rfid,
                    onValueChange = { rfid = it },
                    label = { Text("RFID") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (staffName.isNotBlank() && rfid.isNotBlank()) {
                    onSave(Staff(userName = staffName, rfid = rfid))
                    onDismiss()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
