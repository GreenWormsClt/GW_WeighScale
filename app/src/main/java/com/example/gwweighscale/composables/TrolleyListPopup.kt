package com.example.gwweighscale.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.Rooms.Entities.Tare
import kotlinx.coroutines.launch

@Composable
fun TrolleyListPopup(
    trolleyList: List<Tare>,
    onDismiss: () -> Unit,
    onTrolleySelected: (Tare) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .requiredWidthIn(min = 250.dp, max = 350.dp)
            .wrapContentHeight()
            .padding(16.dp)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium.copy(all = CornerSize(20.dp))
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(trolleyList) { trolley ->
                    Text(
                        text = "${trolley.name} ${trolley.weight} KG",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = InriaSerif,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                onTrolleySelected(trolley) // Pass the selected trolley to the callback
                                onDismiss()
                                coroutineScope.launch {
                                    Toast.makeText(
                                        context,
                                        "Selected ${trolley.name} with weight ${trolley.weight} KG",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                onDismiss()
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End),
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
    }
}
