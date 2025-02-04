package com.example.gwweighscale.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrolleyDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    fontFamily: FontFamily = InriaSerif // Default font family
) {

    var trolleyName by remember { mutableStateOf(TextFieldValue("")) }
    var trolleyWeight by remember { mutableStateOf(TextFieldValue("")) }
    var isErrorName by remember { mutableStateOf(false) }
    var isErrorWeight by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Add Trolley",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                color = Color(0xFF026163),
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Trolley Name
                Text(text = "Trolley Name",
                    color = Color(0xFF026163),
                    fontSize = 14.sp,
                    fontFamily = InriaSerif,
                    modifier = Modifier.padding(bottom = 4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                )  {
                    BasicTextField(
                        value = trolleyName,
                        onValueChange = {
                            trolleyName = it
                            isErrorName = it.text.isEmpty()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = InriaSerif
                        ),
                        singleLine = true
                    )
                    if (trolleyName.text.isEmpty()) {
                        Text(
                            text = "Enter trolley name",
                            color = Color(0xFF026163),
                            fontSize = 14.sp,
                            fontFamily = InriaSerif
                        )
                    }
                }
                if (isErrorName) {
                    Text(
                        text = "Please enter a trolley name",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Trolley Weight
                Text(text = "Trolley Weight (KG)",
                    color = Color(0xFF026163),
                    fontSize = 14.sp,
                    fontFamily = InriaSerif,
                    modifier = Modifier.padding(bottom = 4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                )  {


                    BasicTextField(
                        value = trolleyWeight,
                        onValueChange = {
                            trolleyWeight = it
                            isErrorWeight =
                                it.text.isEmpty() || !it.text.matches(Regex("^\\d+(\\.\\d+)?\$"))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = InriaSerif
                        ),
                        singleLine = true
                    )

                    if (trolleyWeight.text.isEmpty()) {
                        Text(
                            text = "Enter trolley weight",
                            color = Color(0xFF026163),
                            fontSize = 14.sp,
                            fontFamily = InriaSerif
                        )
                    }
                }
                if (isErrorWeight) {
                    Text(
                        text = "Enter a valid weight in KG",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (trolleyName.text.isNotEmpty() && trolleyWeight.text.matches(Regex("^\\d+(\\.\\d+)?\$"))) {
                        onSave(trolleyName.text, trolleyWeight.text)
                    } else {
                        isErrorName = trolleyName.text.isEmpty()
                        isErrorWeight =
                            trolleyWeight.text.isEmpty() || !trolleyWeight.text.matches(Regex("^\\d+(\\.\\d+)?\$"))
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF026163)
                ),

                ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontFamily = InriaSerif,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF026163)
                )
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontFamily = InriaSerif,
                    fontSize = 16.sp
                )
            }
        }
    )
}
