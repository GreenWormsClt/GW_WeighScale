package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search",
    borderColor: Color = Color(0xFF026163),
    iconColor: Color = Color(0xFF026163)
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.4f), fontSize = 10.sp) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = iconColor
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black, // Text color when focused
            unfocusedTextColor = Color.Black, // Text color when unfocused
            focusedBorderColor = borderColor, // Border color when focused
            unfocusedBorderColor = borderColor, // Border color when unfocused
            cursorColor = borderColor, // Cursor color
            focusedLabelColor = Color.Gray, // Placeholder color when focused
            unfocusedLabelColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp) // Rounded corners for the search bar
    )
}
