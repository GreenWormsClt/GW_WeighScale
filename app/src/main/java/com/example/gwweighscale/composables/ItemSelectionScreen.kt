package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.R
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.models.ItemModel
import com.example.gwweighscale.viewmodels.ItemSelectionViewModel
import com.example.gwweighscale.widgets.ConfirmationDialog


@Composable
fun ItemSelectionScreen(viewModel: ItemSelectionViewModel = viewModel(),onNavigateToWeighScale: () -> Unit) {
    val context = LocalContext.current
    val staffName by viewModel.staffName.observeAsState("")
    val weight by viewModel.weight.observeAsState("")
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    val items = remember { viewModel.getItemRows().flatten() }// Get item rows from the ViewModel

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Staff Name and Weight Display
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .align(Alignment.TopEnd) // Align to the top end
        ) {
            Text(
                text = "STAFF NAME: $staffName",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp // Adjust text size as needed
            )
            Spacer(modifier = Modifier.height(4.dp)) // Add space between texts
            Text(
                text = "WEIGHT : $weight KG",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp // Adjust text size as needed
            )
            // Choose Item Text
            Text(
                text = "Choose Item:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InriaSerif, // Apply your desired font family
                modifier = Modifier.align(Alignment.Start) // Align text to the start (left)
            )
            Spacer(modifier = Modifier.height(30.dp))

            val chunkedItems = items.chunked(4) // Group items in rows of 4

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                chunkedItems.forEach { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { item ->
                            ItemButton(
                                item = item,
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
    }

    if (showDialog) {
        ConfirmationDialog(
            selectedItem = selectedItem,
            onConfirm = {
                showDialog = false
                viewModel.selectItem(ItemModel(selectedItem))
                Toast.makeText(
                    context,
                    "Item $selectedItem added successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                onNavigateToWeighScale() // Navigate to WeighScaleScreen on confirmation
            },
            onDismiss = { showDialog = false }
        )
    }
}

    @Composable
fun ItemButton(item: String, onItemSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp) // Set custom width for rectangle
            .height(80.dp) // Set custom height for rectangle
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewItemSelectionScreen() {
//    ItemSelectionScreen(
//
//        viewModel = ItemSelectionViewModel().apply {
//            // Optionally, set initial values or mock data for the preview
//        }
//    )
//}
