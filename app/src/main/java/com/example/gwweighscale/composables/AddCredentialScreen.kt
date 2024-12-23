package com.example.gwweighscale.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gwweighscale.viewmodels.LoginViewModel

@Composable
fun AddCredentialScreen(viewModel: LoginViewModel, navController: NavController) {
    var machineCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add User",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = Color(0xFF026163),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                elevation = 4.dp
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Machine Code TextField
                OutlinedTextField(
                    value = machineCode,
                    onValueChange = { machineCode = it },
                    label = { Text("Machine Code") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF026163), // Set focused border color to GWGreen
                        unfocusedBorderColor = Color(0xFF026163), // Set unfocused border color to GWGreen
                        errorBorderColor = Color.Red, // Set error border color
                        focusedLabelColor = Color(0xFF026163), // Set focused label color to GWGreen
                        unfocusedLabelColor = Color(0xFF026163) // Set unfocused label color to GWGreen
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF026163), // Set focused border color to GWGreen
                        unfocusedBorderColor = Color(0xFF026163), // Set unfocused border color to GWGreen
                        errorBorderColor = Color.Red, // Set error border color
                        focusedLabelColor = Color(0xFF026163), // Set focused label color to GWGreen
                        unfocusedLabelColor = Color(0xFF026163) // Set unfocused label color to GWGreen
                    ),
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Add Credential Button
                Button(
                    onClick = {
                        if (machineCode.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.addCredential(machineCode, password)
                            navController.navigate("login")
                        }
                    },
                    modifier = Modifier
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF026163),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Add User",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}
