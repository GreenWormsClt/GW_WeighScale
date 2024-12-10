package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.rooms.entities.Tare
import kotlinx.coroutines.launch
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrolleyListPopup(
    trolleyList: List<Tare>,
    onDismiss: () -> Unit,
    onTrolleySelected: (Tare) -> Unit,
    onTrolleyDeleted: (Tare) -> Unit, // Callback for deletion
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var trolleyToDelete by remember { mutableStateOf<Tare?>(null) }
    var longPressedTrolley by remember { mutableStateOf<Tare?>(null) } // Track long-pressed trolley

    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium.copy(all = CornerSize(20.dp))
            )
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Text(
                text = "Trolley List",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF026163),
                textAlign = TextAlign.Center,// GWGreen color
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(trolleyList) { trolley ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTrolleySelected(trolley); onDismiss() }
                            .combinedClickable(
                                onClick = {
                                    onTrolleySelected(trolley)
                                    onDismiss()
                                    coroutineScope.launch {
                                        Toast.makeText(
                                            context,
                                            "Selected ${trolley.name} with weight ${trolley.weight} KG",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                onLongClick = { longPressedTrolley = trolley }
                            ),
                      //  elevation = 4.dp,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = trolley.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = InriaSerif,
                                    color = Color(0xFF333333)
                                )
                                Text(
                                    text = "${trolley.weight} KG",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }

                            if (longPressedTrolley == trolley) { // Show delete button only for long-pressed trolley
                                Button(
                                    onClick = {
                                        trolleyToDelete = trolley
                                        showDeleteDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red // GWGreen color
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text(
                                        text = "Delete",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.run { buttonColors(containerColor = Color.White) },
                ) {
                    Text(
                        text = "Close",
                        color = Color(0xFF026163),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Confirmation dialog for deletion
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Confirm Deletion",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete ${trolleyToDelete?.name}?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        trolleyToDelete?.let { onTrolleyDeleted(it) }
                        showDeleteDialog = false
                        longPressedTrolley = null // Reset long-pressed trolley
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF026163) // GWGreen color
                    )
                ) {
                    Text("Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
