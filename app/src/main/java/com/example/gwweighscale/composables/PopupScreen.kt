package com.example.gwweighscale.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.gestures.detectTapGestures
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.models.PopupData
import androidx.compose.material3.Button
import kotlinx.coroutines.launch

@Composable
fun PopupScreen(
    reportDetails: List<PopupData>,
    onDismiss: () -> Unit,
    onResetReports: () -> Unit,
    exportToExcel: (Context) -> Unit,
    context: Context
) {
    var showFirstAlert by remember { mutableStateOf(false) }
    var showSecondAlert by remember { mutableStateOf(false) }
    var showDeleteIcon by remember { mutableStateOf(false) } // Toggle Delete Icon on Long Press

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .size(width = 924.dp, height = 609.dp)
                .padding(16.dp),
            color = androidx.compose.material3.MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Title Row with Long-Press Logic
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        showDeleteIcon = true
                                    }
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Report Details",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InriaSerif,
                            color = Color(0xFF026163),
                            textAlign = TextAlign.Start
                        )

                        if (showDeleteIcon) {
                            IconButton(
                                onClick = {
                                    if (reportDetails.isEmpty()) {
                                        Toast.makeText(context, "There is no data to reset.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        showFirstAlert = true
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = Color.Red.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }

                        Button(
                            onClick = { exportToExcel(context) },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Download Excel")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Table Header
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.1f
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            "Staff Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Item Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Weight (Kg)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Date",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Time",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Data Rows
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        reportDetails.forEach { detail ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .background(
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant.copy(
                                            alpha = 0.2f
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    detail.staffName,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    detail.itemName,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "${detail.weight} Kg",
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    detail.date,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    detail.time,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Close Button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(48.dp)
                            .background(
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error.copy(
                                    alpha = 0.2f
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

    // First Confirmation Dialog
    if (showFirstAlert) {
        AlertDialog(
            onDismissRequest = { showFirstAlert = false },
            title = { Text("Confirm Reset") },
            text = { Text("Are you sure you want to reset the reports?") },
            confirmButton = {
                Button(onClick = {
                    showFirstAlert = false
                    showSecondAlert = true
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showFirstAlert = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showSecondAlert) {
        AlertDialog(
            onDismissRequest = { showSecondAlert = false },
            title = { Text("Final Confirmation") },
            text = { Text("This action cannot be undone. Proceed?") },
            confirmButton = {
                Button(onClick = {
                    showSecondAlert = false
                    onResetReports()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showSecondAlert = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
