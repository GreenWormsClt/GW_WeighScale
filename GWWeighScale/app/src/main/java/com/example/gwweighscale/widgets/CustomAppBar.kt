package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: String,
    iconRes: Int,
    onLogoutClick: () -> Unit,
    onExitClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth() // This makes the text take up full width
            )
        },
        actions = {
            IconButton(
                onClick = { menuExpanded = true }  // Show the dropdown menu
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "More Options",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            // Dropdown menu
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        menuExpanded = false
                        onLogoutClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exit") },
                    onClick = {
                        menuExpanded = false
                        onExitClick()
                    }
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White // Set the app bar background color to white
        ),
        modifier = Modifier.fillMaxWidth()
    )
}