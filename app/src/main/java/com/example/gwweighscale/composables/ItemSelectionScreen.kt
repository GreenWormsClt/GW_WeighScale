package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.R
//import com.example.gwweighscale.Rooms.Entities.ItemReport
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.viewmodels.BluetoothViewModel
import com.example.gwweighscale.viewmodels.ItemSelectionViewModel
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import com.example.gwweighscale.widgets.ConfirmationDialog
import com.example.gwweighscale.widgets.ItemSelectionAlert

@Composable
fun ItemSelectionScreen(
    viewModel: ItemSelectionViewModel = viewModel(),
    weighviemodel: WeighScaleViewModel = viewModel(),
    onNavigateToWeighScale: () -> Unit,
    staffName: String,
    trolleyName: String,
    trolleyWeight: String,
    netWeight: String
) {
    val context = LocalContext.current
    val items by viewModel.allItems.observeAsState(emptyList())


    val displayedItems = remember { items.take(12) } // Always display the first 12 items
    val selectedItems = remember { mutableStateListOf<String>() } // Dynamically added items

    var showDialog by remember { mutableStateOf(false) }
    var showItemPopup by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopEnd)
        ) {
            Text(
                text = "STAFF NAME:  $staffName",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "WEIGHT: $netWeight",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "TROLLEY: $trolleyName, $trolleyWeight KG",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Choose Item:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InriaSerif,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Combine fixed items and dynamically added items
            val allItems = displayedItems.map { it.itemName } + selectedItems

            // Group items into rows of 4
            val chunkedItems = allItems.chunked(4)

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chunkedItems.forEach { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { itemName ->
                            ItemButton(
                                item = itemName,
                                onItemSelected = { selectedItemName ->
                                    selectedItem = selectedItemName
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { showItemPopup = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = colorResource(id = R.color.GWGreen)
        ) {
            Text("+", color = Color.White, fontSize = 24.sp)
        }
    }

    if (showDialog) {
        ConfirmationDialog(
            selectedItem = selectedItem,
            onConfirm = {
                showDialog = false
                Toast.makeText(
                    context,
                    "Item $selectedItem added successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                onNavigateToWeighScale()
            },
            onDismiss = { showDialog = false }
        )
    }

    if (showItemPopup) {
        // Filter items to display only the 13th and beyond
        val popupItems = items.drop(12).map { it.itemName } // Exclude the first 12 items

        ItemSelectionAlert(
            popupItems = popupItems, // Pass only the 13th and beyond items
            onItemSelected = { selectedItemName ->
                // Add the selected item's name to the dynamic list
                if (selectedItemName !in selectedItems) {
                    selectedItems.add(selectedItemName)
                    Toast.makeText(
                        context,
                        "Item $selectedItemName added to the list!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Item $selectedItemName is already added!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                showItemPopup = false
            },
            onDismissRequest = { showItemPopup = false }
        )
    }
}



@Composable
fun ItemButton(item: String, onItemSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(80.dp)
            .shadow(5.dp, RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.GWGreen), RoundedCornerShape(8.dp))
            .clickable { onItemSelected(item) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item,
            color = Color.White,
            fontFamily = InriaSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}