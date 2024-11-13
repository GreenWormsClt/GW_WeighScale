package com.example.gwweighscale.fontfamily

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.R


val InriaSerif = FontFamily(
    Font(R.font.inria_sherif_regular, FontWeight.Normal),
    Font(R.font.inria_sherif_bold, FontWeight.Bold)
)

// Create a custom Typography using the font
val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = InriaSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = InriaSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    // Add more text styles if needed
)

@Composable
fun CustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = CustomTypography,
        content = content
    )
}