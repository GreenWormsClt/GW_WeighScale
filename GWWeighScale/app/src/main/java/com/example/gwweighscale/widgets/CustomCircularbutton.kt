package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.R

@Composable
fun CircleButton(text: String, isTablet: Boolean) {
    Button(
        onClick = { /* Handle click */ },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.GWGreen)),
        modifier = Modifier
            .size(if (isTablet) 110.dp else 55.dp)  // Adjust button size for tablet and phone
            .shadow(
            elevation = 50.dp, // Set the elevation of the shadow
            shape = CircleShape
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = if (isTablet) 20.sp else 14.sp  // Adjust font size for button text
        )
    }
}
