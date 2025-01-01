package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gwweighscale.rooms.entities.Staff
import com.example.gwweighscale.viewmodels.WeighScaleViewModel

@Composable
fun StaffListScreen(
    viewModel: WeighScaleViewModel,
    navController: NavController
) {
    val staffList by viewModel.allStaffs.observeAsState(emptyList())
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var staffToDelete by remember { mutableStateOf<Staff?>(null) }
    val expectedCode = "GW@2025"
    var verificationCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Staff List", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF026163), // Custom color for the app bar
                contentColor = Color.White,
                elevation = 4.dp
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(staffList) { staff ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        elevation = 6.dp,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color(0xFFE8F5F8)) // Light background color for the card
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Name: ${staff.userName}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF004D4D) // Dark teal color for text
                                )
                                Text(
                                    text = "RFID: ${staff.rfid}",
                                    fontSize = 16.sp,
                                    color = Color(0xFF026163) // Slightly lighter teal
                                )
                            }
                            Button(
                                onClick = {
                                    staffToDelete = staff
                                    showDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFFF6F61), // Soft red for delete button
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Staff") },
            text = {
                Column {
                    Text("Please enter the verification code to delete ${staffToDelete?.userName}.")
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = verificationCode,
                        onValueChange = { verificationCode = it },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            ) {
                                if (verificationCode.isEmpty()) {
                                    Text(
                                        text = "Enter Verification Code",
                                        color = Color.Black
                                    )
                                }
                                innerTextField()
                            }
                        },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (verificationCode == expectedCode) {
                            staffToDelete?.let {
                                viewModel.deleteStaff(it)
                                Toast.makeText(context, "Deleted ${it.userName}", Toast.LENGTH_SHORT).show()
                            }
                            showDialog = false
                            verificationCode = "" // Reset the code
                        } else {
                            Toast.makeText(context, "Incorrect verification code!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false
                        verificationCode = ""},

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
