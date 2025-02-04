package com.example.gwweighscale.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.gwweighscale.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    borderColor: Color = colorResource(id = R.color.GWGreen),
    labelColor: Color = colorResource(id = R.color.GWGreen), // Default label color
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = borderColor, // Set focused border color to GWGreen
                unfocusedBorderColor = borderColor, // Set unfocused border color to GWGreen
                errorBorderColor = MaterialTheme.colorScheme.error, // Set error border color
                focusedLabelColor = labelColor, // Set focused label color to GWGreen
                unfocusedLabelColor = labelColor // Set unfocused label color to GWGreen
            ),
            singleLine = true, // Add this line
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black), // Add this line
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error, // Error message color
                style = MaterialTheme.typography.bodySmall // Error message typography
            )
        }
    }
}