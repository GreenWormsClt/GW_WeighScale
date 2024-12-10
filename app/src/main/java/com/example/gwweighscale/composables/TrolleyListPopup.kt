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

    Box(
        modifier = modifier
            .requiredWidthIn(min = 250.dp, max = 350.dp)
            .wrapContentHeight()
            .padding(16.dp)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium.copy(all = CornerSize(20.dp))
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(trolleyList) { trolley ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White)
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
                                onLongClick = {
                                    trolleyToDelete = trolley
                                    showDeleteDialog = false
                                }
                            )
                    ) {
                        Text(
                            text = "${trolley.name} ${trolley.weight} KG",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = InriaSerif,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                        if (trolleyToDelete == trolley) {
                            IconButton(
                                onClick = {
                                    trolleyToDelete = trolley
                                    showDeleteDialog = true
                                },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Delete Trolley",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = onDismiss, // Handle dismiss action
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Close",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
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
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.onError)
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
