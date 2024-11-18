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
    dialogBackgroundColor: Color = Color.Black
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
        backgroundColor = Color.Transparent,
        title = null,
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .heightIn(min = 200.dp, max = 400.dp) // Constrain dialog height
                    .clip(RoundedCornerShape(12.dp))
                    .background(dialogBackgroundColor)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Assign weight to allow scrolling in a constrained height
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column {
                            filteredItems.forEach { item ->
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .background(Color.Gray, RoundedCornerShape(8.dp))
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
            }
        },
        buttons = {}
    )
}

