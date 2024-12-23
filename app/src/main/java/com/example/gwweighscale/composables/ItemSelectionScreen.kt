package com.example.gwweighscale.composables

import android.adservices.adid.AdId
import android.util.Log
import kotlin.random.Random
import java.math.BigInteger
import java.security.MessageDigest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gwweighscale.R
//import com.example.gwweighscale.Rooms.Entities.ItemReport
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.rooms.entities.Item
import com.example.gwweighscale.rooms.entities.WeighScale
import com.example.gwweighscale.utils.generateColorFromString
import com.example.gwweighscale.viewmodels.BluetoothViewModel
import com.example.gwweighscale.viewmodels.ItemSelectionViewModel
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import com.example.gwweighscale.widgets.AddItemDialog
import com.example.gwweighscale.widgets.ConfirmationDialog
import com.example.gwweighscale.widgets.CountdownDialog
import com.example.gwweighscale.widgets.ItemSelectionAlert
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ItemSelectionScreen(
    viewModel: ItemSelectionViewModel = viewModel(),
    weighviemodel: WeighScaleViewModel = viewModel(),
    onNavigateToWeighScale: () -> Unit,
    onNavigateToItemList:() -> Unit,
    staffName: String,
    trolleyName: String,
    trolleyWeight: String,
    netWeight: String,
    staffId: Int,
    machineCode: String
) {
    val context = LocalContext.current
    val items by viewModel.allItems.observeAsState(emptyList())
    var showAddItemDialog by remember { mutableStateOf(false) }
    var newItemName by remember { mutableStateOf("") }


    val configuration = LocalConfiguration.current
    fun cleanAndParseWeight(weight: String): Float {
        return weight.replace("[^0-9.]".toRegex(), "").toFloatOrNull() ?: 0.0f
    }

    // Parse weights
    val tNetWeight = cleanAndParseWeight(netWeight)
    val tTrolleyWeight = cleanAndParseWeight(trolleyWeight)

    // Calculate total weight
    val totalWeight = tNetWeight - tTrolleyWeight
    val formattedTotalWeight = String.format("%.3f", totalWeight)
    var showCountdownDialog by remember { mutableStateOf(false) }
    val countdownMinutes = 15

    val columns =
        if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            2
        } else {
            4
        }
    val displayedItems = items.take(12)  // Always display the first 12 items
    var selectedItems = remember { mutableStateListOf<String>() } // Dynamically added items
    var allItems = displayedItems.map { it.itemName } + selectedItems

    var showDialog by remember { mutableStateOf(false) }
    var showItemPopup by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var weighScale by remember { mutableStateOf<WeighScale?>(null) }
    var loading by remember { mutableStateOf(true) }

    // Fetch weigh scale asynchronously
    LaunchedEffect(machineCode) {
        Log.d("DEBUG", "Fetching WeighScale with code: $machineCode")
        weighviemodel.fetchWeighScaleByCode(machineCode) { result ->
            weighScale = result
            loading = false
            if (result == null) {
                Log.e("DEBUG", "WeighScale not found for code: $machineCode")
            } else {
                Log.d("DEBUG", "WeighScale found: $result")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Choose Item",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToWeighScale() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }

                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                androidx.compose.material3.Text(
                                    "Add items"
                                )
                            },
                            onClick = {
                                showMenu = false
                                showAddItemDialog = true
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                androidx.compose.material3.Text("List Items")
                            },
                            onClick = {
                                showMenu = false
                                onNavigateToItemList()
                            }
                        )

                    }

                },
                backgroundColor = Color(0xFF026163), // Custom color for the app bar
                contentColor = Color.White,
                elevation = 4.dp
            )
        },
        content = { paddingValues   ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
//                .verticalScroll(rememberScrollState())
                        .align(Alignment.TopEnd)
                ) {

                    Text(
                        text = "STAFF NAME:  $staffName",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "WEIGHT: $formattedTotalWeight KG",
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

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns), // 4 columns
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(allItems) { item ->
                            ItemButton(
                                item = item,
                                onItemSelected = { selectedItemName ->
                                    selectedItem = selectedItemName
                                    showDialog = true
                                })
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
        }
    )


    if (showDialog) {
        // Get the selected item's ID
        val selectedItemId = items.find { it.itemName == selectedItem }?.itemId ?: 0

        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        // Convert the formatted weight string to a Double
        val selectedWeight = formattedTotalWeight.toDoubleOrNull() ?: 0.0

        ConfirmationDialog(
            selectedItem = selectedItem,
            onConfirm = {
                if (weighScale != null) {
                    viewModel.insertReport(
                        mrfId = weighScale!!.mrfId,
                        plantId = weighScale!!.plantId,
                        machineId = weighScale!!.machineId,
                        weight = selectedWeight,
                        userId = staffId,
                        itemId = selectedItemId,
                        date = currentDate,
                        time = currentTime,
                        onDuplicate = {
                            showCountdownDialog = true
                        },
                        onSuccess = {
                            Toast.makeText(
                                context,
                                "Item $selectedItem added successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Save the trolley and selected item data to savedStateHandle

                            // Navigate back to the WeighScaleScreen
                            onNavigateToWeighScale()
                        }
                    )
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    if (showCountdownDialog) {
        CountdownDialog(
            onDismiss = { showCountdownDialog = false },
            initialMinutes = countdownMinutes,
            onTimeOut = {
                showCountdownDialog = false
                // Action to perform after the countdown ends
            }
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

    if (showAddItemDialog) {
        AddItemDialog(
            newItemName = newItemName,
            onValueChange = { newItemName = it },
            onConfirm = {
                if (newItemName.isNotBlank()) {
                    viewModel.insertItem(Item(itemId = 0, itemName = newItemName))
                    Toast.makeText(context, "Item added successfully!", Toast.LENGTH_SHORT).show()
                    newItemName = ""
                    showAddItemDialog = false
                } else {
                    Toast.makeText(context, "Item name cannot be empty!", Toast.LENGTH_SHORT).show()
                }
            },
            onDismiss = { showAddItemDialog = false }
        )
    }


}


    @Composable
    fun ItemButton(item: String, onItemSelected: (String) -> Unit) {
        // Generate a static color based on the item string
        val staticColor = remember(item) {
            generateColorFromString(item)
        }

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(staticColor, RoundedCornerShape(8.dp))
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

