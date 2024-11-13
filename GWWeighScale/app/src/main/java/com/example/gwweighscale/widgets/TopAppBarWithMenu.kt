package com.example.gwweighscale.widgets

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun TopAppBarWithMenu() {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("My App") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        // Handle Logout action
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exit") },
                    onClick = {
                        // Handle Exit action
                        expanded = false
                    }
                )
            }
        }
    )
}