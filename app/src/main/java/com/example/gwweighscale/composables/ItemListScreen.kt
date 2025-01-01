package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.viewmodels.ItemSelectionViewModel

@Composable
fun ItemListScreen(
    viewModel: ItemSelectionViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val items by viewModel.allItems.observeAsState(emptyList()) // Observing live data for items
    var verificationCode by remember { mutableStateOf("") }
    val correctCode = "GW@2025"
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Item?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Item List",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF026163), // Custom color for the app bar
                contentColor = Color.White,
                elevation = 4.dp
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { item ->
                    ItemCard(
                        item = item,
                        onDelete = {
                            selectedItem = item
                            showDialog = true
                        }
                    )
                }
            }
        }
    )

    // Confirmation AlertDialog
    if (showDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Close dialog on dismiss
            title = {
                Text(text = "Confirm Delete", fontWeight = FontWeight.Bold)
            },
            text = {

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
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (verificationCode == correctCode) {
                            selectedItem?.let {
                                viewModel.deleteItem(it)
                                Toast.makeText(
                                    context,
                                    "${it.itemName} deleted!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            showDialog = false
                            verificationCode = ""
                        } else {
                            Toast.makeText(
                                context,
                                "Incorrect verification code!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                        verificationCode = ""
                    },
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

@Composable
fun ItemCard(
    item: Item,
    onDelete: (Item) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5F8))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.itemName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF004D4D),
                modifier = Modifier.padding(start = 8.dp)
            )
            Button(
                onClick = { onDelete(item) },
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
