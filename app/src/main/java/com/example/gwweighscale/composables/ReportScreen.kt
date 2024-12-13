package com.example.gwweighscale.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.models.PopupData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(
    reportDetails: List<PopupData>, // List of report details
    onBackClick: () -> Unit // Callback for back button click
) {
    // Group data by staff name, then by item name, and sum weights
    val groupedData = reportDetails.groupBy { it.staffName }
        .mapValues { (_, entries) ->
            entries.groupBy { it.itemName }.mapValues { (_, items) ->
                items.sumOf { it.weight }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Enable vertical scrolling
        horizontalAlignment = Alignment.Start // Align text to start
    ) {
        // Display the current date
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        Text(
            text = "Report Date: $currentDate",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display grouped data
        groupedData.forEach { (staffName, itemData) ->
            // Staff Name Header
            Text(
                text = staffName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            // Item Details for the Staff
            itemData.forEach { (itemName, totalWeight) ->
                Text(
                    text = "- $itemName: ${totalWeight}kg",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                )
            }
        }

        // Back Button
        Button(
            onClick = onBackClick, // Handle back navigation
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text(text = "Back")
        }
    }
}
