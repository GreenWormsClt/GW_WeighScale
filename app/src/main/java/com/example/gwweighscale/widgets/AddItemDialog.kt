package com.example.gwweighscale.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif

@Composable
fun AddItemDialog(
    newItemName: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dialogTitle: String = "Add New Item"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = dialogTitle,
                color = Color(0xFF026163), // Title color
                fontSize = 22.sp,
                fontFamily = InriaSerif,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) // Dialog content background
                    .padding(16.dp)
            ) {
                Text(
                    text = "Enter item name:",
                    fontSize = 16.sp,
                    fontFamily = InriaSerif,
                    color = Color(0xFF026163),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Custom BasicTextField
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFE8E8E8), // Light gray background for input
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    if (newItemName.isEmpty()) {
                        Text(
                            text = "Item name",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = InriaSerif
                        )
                    }
                    BasicTextField(
                        value = newItemName,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = InriaSerif
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF026163) // Button background color
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = InriaSerif
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF026163) // Button background color
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = InriaSerif
                )
            }
        },
        backgroundColor = Color.White, // Dialog background color
        contentColor = Color.Black // Content color
    )
}
