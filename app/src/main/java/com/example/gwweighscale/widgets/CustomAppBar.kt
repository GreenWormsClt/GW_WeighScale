package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    iconRes: Int,
    onLogoutClick: () -> Unit,
    onExitClick: () -> Unit,
    onNavigateToLogin: () -> Unit, // Add a navigation callback for login
    fontFamily: FontFamily = InriaSerif,
    imageRes: Int,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) } // State to show/hide the dialog

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically // Center image and text vertically
            ) {
                // Image on the left
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Custom Image",
                    modifier = Modifier.size(40.dp), // Increased size for visibility
                    tint = Color.Unspecified // Remove any tint to ensure image color is preserved
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add space between image and title

                // Title text
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        actions = {
            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "More Options",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Add Trolley",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        expanded = false
                        onExitClick()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Logout",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        expanded = false
                        showLogoutDialog = true // Show the logout confirmation dialog
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Exit",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        expanded = false
                        onExitClick()
                    }
                )

            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )

    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Are you sure?",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Do you want to log out?",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onNavigateToLogin() // Navigate to the login screen
                    }
                ) {
                    Text(
                        text = "Yes",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false } // Dismiss the dialog
                ) {
                    Text(
                        text = "No",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}