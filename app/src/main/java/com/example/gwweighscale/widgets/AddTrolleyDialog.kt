package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrolleyDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    fontFamily: FontFamily = InriaSerif // Default font family
) {
    val customColor = Color(0xFF026163) // #026163 color
    var trolleyName by remember { mutableStateOf("") }
    var trolleyWeight by remember { mutableStateOf("") }
    var isErrorName by remember { mutableStateOf(false) }
    var isErrorWeight by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Add Trolley",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                color = Color.Black // Title text color
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Trolley Name
                OutlinedTextField(
                    value = trolleyName,
                    onValueChange = {
                        trolleyName = it
                        isErrorName = it.isEmpty()
                    },
                    label = { Text("Trolley Name", color = customColor) }, // Label text color
                    modifier = Modifier.fillMaxWidth(),
                    isError = isErrorName,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = customColor,
                        unfocusedBorderColor = customColor,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        focusedTextColor = Color.Black, // Corrected text color for focused state
                        unfocusedTextColor = customColor, // Corrected text color for unfocused state
                        cursorColor = customColor // Cursor color
                    )
                )
                if (isErrorName) {
                    Text(
                        text = "Please enter a trolley name",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Trolley Weight
                OutlinedTextField(
                    value = trolleyWeight,
                    onValueChange = {
                        trolleyWeight = it
                        isErrorWeight = it.isEmpty() || !it.matches(Regex("^\\d+(\\.\\d+)?\$"))
                    },
                    label = { Text("Trolley Weight (KG)", color = customColor) }, // Label text color
                    modifier = Modifier.fillMaxWidth(),
                    isError = isErrorWeight,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = customColor,
                        unfocusedBorderColor = customColor,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        focusedTextColor = Color.Black, // Corrected text color for focused state
                        unfocusedTextColor = customColor, // Corrected text color for unfocused state
                        cursorColor = customColor // Cursor color
                    )
                )

                if (isErrorWeight) {
                    Text(
                        text = "Enter a valid weight in KG",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (trolleyName.isNotEmpty() && trolleyWeight.matches(Regex("^\\d+(\\.\\d+)?\$"))) {
                        onSave(trolleyName, trolleyWeight)
                    } else {
                        isErrorName = trolleyName.isEmpty()
                        isErrorWeight = trolleyWeight.isEmpty() || !trolleyWeight.matches(Regex("^\\d+(\\.\\d+)?\$"))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColor, // Save button background color
                    contentColor = Color.White // Save button text color
                )
            ) {
                Text("Save", fontFamily = fontFamily, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = customColor // Cancel button text and border color
                )
            ) {
                Text("Cancel", fontFamily = fontFamily, fontWeight = FontWeight.Normal)
            }
        },
        shape = MaterialTheme.shapes.medium, // Rounded corners
        containerColor = Color.White // Background color for the dialog
    )
}
