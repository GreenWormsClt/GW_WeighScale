package com.example.gwweighscale.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.fontfamily.InriaSerif

@Composable
fun AddStaffDialog(
    onDismiss: () -> Unit,
    onSave: (Staff) -> Unit
) {
    var staffName by remember { mutableStateOf("") }
    var rfid by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White, // Dialog Background
        titleContentColor = Color(0xFF026163), // Title Color
        textContentColor = Color.Black,
        iconContentColor = Color(0xFF026163), // Icon Color
        shape = RoundedCornerShape(16.dp), // Rounded Corners
        title = {
            Text(
                text = "Add New Staff",
                fontSize = 22.sp,
                fontFamily = InriaSerif,
                color = Color(0xFF026163),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Staff Name Field
                Text(
                    text = "Staff Name",
                    color = Color(0xFF026163),
                    fontSize = 14.sp,
                    fontFamily = InriaSerif,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    BasicTextField(
                        value = staffName,
                        onValueChange = { staffName = it },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = InriaSerif
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (staffName.isEmpty()) {
                        Text(
                            text = "Enter staff name",
                            color = Color(0xFF026163),
                            fontSize = 14.sp,
                            fontFamily = InriaSerif
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // RFID Field
                Text(
                    text = "RFID",
                    color = Color(0xFF026163),
                    fontSize = 14.sp,
                    fontFamily = InriaSerif,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    BasicTextField(
                        value = rfid,
                        onValueChange = { rfid = it },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = InriaSerif
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (rfid.isEmpty()) {
                        Text(
                            text = "Enter RFID",
                            color = Color(0xFF026163),
                            fontSize = 14.sp,
                            fontFamily = InriaSerif
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (staffName.isNotBlank() && rfid.isNotBlank()) {
                        onSave(Staff(userName = staffName, rfid = rfid))
                        onDismiss()
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF026163)
                        ),

            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontFamily = InriaSerif,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,


                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF026163)
                )
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontFamily = InriaSerif,
                    fontSize = 16.sp
                )
            }
        }
    )
}
