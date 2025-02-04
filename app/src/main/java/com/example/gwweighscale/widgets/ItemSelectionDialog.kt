package com.example.gwweighscale.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.widgets.SearchBar

@Composable
fun ItemSelectionAlert(
    popupItems: List<String>,
    onItemSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    dialogBackgroundColor: Color = Color.White
) {
    var searchText by remember { mutableStateOf("") }

    val filteredItems = remember(searchText) {
        popupItems.filter { it.contains(searchText, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        backgroundColor = Color.Transparent, // Keep dialog background transparent
        title = null,
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Limit dialog width to 90% of the screen
                    .heightIn(max = 400.dp) // Constrain the height of the dialog
                    .clip(RoundedCornerShape(12.dp)) // Rounded corners
                    .background(dialogBackgroundColor) // Apply the custom background color
                    .padding(16.dp) // Add padding inside the dialog
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title and Close Icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Select an Item",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF026163)
                        )
                        IconButton(
                            onClick = onDismissRequest,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color(0xFF026163)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search Bar
                    SearchBar(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = "Search",
                        borderColor = Color(0xFF026163),
                        iconColor = Color(0xFF026163),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Scrollable list of filtered items
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 250.dp) // Limit the height of the scrollable area
                            .verticalScroll(rememberScrollState()) // Enable scrolling only for items
                    ) {
                        filteredItems.forEach { item ->
                            Text(
                                text = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFF026163), RoundedCornerShape(8.dp))
                                    .clickable {
                                        onItemSelected(item)
                                    }
                                    .padding(vertical = 10.dp, horizontal = 16.dp),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = InriaSerif
                            )
                        }
                    }
                }
            }
        },
        buttons = {}
    )
}
