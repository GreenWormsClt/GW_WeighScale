package com.example.gwweighscale.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif
import kotlinx.coroutines.delay

@Composable
fun CountdownDialog(
    onDismiss: () -> Unit,
    initialMinutes: Int,
    onTimeOut: () -> Unit
) {
    var totalTimeLeft by remember { mutableStateOf(initialMinutes * 60) } // Convert minutes to seconds

    // Countdown logic
    LaunchedEffect(Unit) {
        while (totalTimeLeft > 0) {
            delay(1000L) // 1 second delay
            totalTimeLeft--
        }
        onTimeOut() // Trigger the timeout callback when the countdown ends
    }

    val minutes = totalTimeLeft / 60
    val seconds = totalTimeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) // Title background color
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Duplicate Entry Detected!",
                    color = Color(0xFF026163), // Title text color
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InriaSerif
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please wait for the countdown to finish before trying again.",
                    fontSize = 16.sp,
                    fontFamily = InriaSerif,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Time remaining: $formattedTime",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InriaSerif,
                    color = Color.Red
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF026163) // Button color
                )
            ) {
                Text(
                    text = "OK",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = InriaSerif
                )
            }
        },
        backgroundColor = Color.White, // Dialog background color
        contentColor = Color.Black // Content color
    )
}
