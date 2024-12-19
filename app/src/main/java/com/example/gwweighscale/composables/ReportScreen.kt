package com.example.gwweighscale.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.models.ReportData

@Composable
fun ReportScreen(
    summaryDetails: List<ReportData>,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title
            Text(
                text = "Summary Report",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF026163),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            // Group data by staff name and display
            if (summaryDetails.isNotEmpty()) {
                val groupedData = summaryDetails.groupBy { it.staffName }

                groupedData.forEach { (staffName, staffData) ->
                    // Display date (Assume all items for the same staff are on the same date)
                    Text(
                        text = "Staff Name: $staffName",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    staffData.forEach { data ->
                        Text(
                            text = "Item: ${data.itemName}, Total Weight: ${data.totalWeight} KG",
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Text(
                    text = "No data available",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Close Button aligned at the bottom
        androidx.compose.material.IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align at the bottom
                .padding(16.dp)
                .size(48.dp)
                .background(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error.copy(
                        alpha = 0.2f
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            androidx.compose.material.Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = androidx.compose.material3.MaterialTheme.colorScheme.error
            )
        }
    }
}
