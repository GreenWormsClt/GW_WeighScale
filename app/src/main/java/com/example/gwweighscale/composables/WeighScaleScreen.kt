package com.example.gwweighscale.composables

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gwweighscale.widgets.CustomAppBar
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.R
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.zIndex
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gwweighscale.viewmodels.BluetoothViewModel


@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighScaleScreen(
    machineCode: String,
    bluetoothViewModel: BluetoothViewModel,
    viewModel: WeighScaleViewModel = viewModel(),
    onNavigateToLogin: () -> Unit, // Pass the navigation callback to LoginScreen
    onNavigateToItemSelection: (String) -> Unit //
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth > 600.dp
    val scrollState = rememberScrollState()

    // Accessing the ViewModel states
    val isPopupVisible by viewModel.isPopupVisible
    val popupData by viewModel.popupData
    val staff by viewModel.allStaffs.observeAsState(emptyList())
    val isTrolleyPopupVisible by viewModel.isTrolleyPopupVisible
    val trolleyList by viewModel.trolleyList
    val rfidMatch by viewModel.rfidMatch.observeAsState()
    var rfidTag by remember { mutableStateOf("") }
    var matchedStaffName by remember { mutableStateOf<String?>(null) }

    val focusRequester = remember { FocusRequester() }
    val view = LocalView.current
    val netweight by bluetoothViewModel.netweight
    val time by bluetoothViewModel.time
    val context = LocalContext.current
    val date by bluetoothViewModel.date


    LaunchedEffect(Unit) {
        val windowInsetsController = ViewCompat.getWindowInsetsController(view)
        windowInsetsController?.hide(WindowInsetsCompat.Type.ime()) // Hide the keyboard explicitly
        focusRequester.requestFocus() // Ensure focus is on the TextField // Ensure focus is on the TextField
        bluetoothViewModel.connect()// Replace with actual Bluetooth address of your Essae scale

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isTablet) 10.dp else 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = if (isTablet) 10.dp else 10.dp) // Adjusted top padding for the app bar space
        ) {
            // Add the CustomAppBar at the top
            CustomAppBar(
                imageRes = R.drawable.gwicon,
                title = "GW Weigh Scale",
                iconRes = R.drawable.threedots,
                onLogoutClick = {
                    onNavigateToLogin() // Navigate to the Login screen
                },
                onExitClick = { /* Handle Exit action */ },
                modifier = Modifier.zIndex(1f),
                onNavigateToLogin = onNavigateToLogin// Pass the callback for login navigation // Optional, you can pass a modifier if needed
            )

            Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "DATE : $date ",
                    fontSize = if (isTablet) 20.sp else 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InriaSerif
                )
                Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))
                Text(
                    text = "TIME : $time",
                    fontSize = if (isTablet) 20.sp else 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InriaSerif
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            // Removed the Image and its surrounding Column
            Column(modifier = Modifier.fillMaxSize()) {
                var matchedStaffName by remember { mutableStateOf<String?>(null) }

                TextField(
                    value = rfidTag,
                    onValueChange = { inputRfid ->
                        rfidTag = inputRfid
                        matchedStaffName = viewModel.validateRfidAndFetchStaffName(inputRfid)
                        if (matchedStaffName == null) {
                          //  Toast.makeText(context, "RFID not found", Toast.LENGTH_SHORT).show()
                        } else {
                            onNavigateToItemSelection(matchedStaffName!!)
                        }
                    },
                    label = { Text("RFID Tag") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                       .alpha(1f),
//                        readOnly = true,
//                    enabled = false,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.None
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (isTablet) 40.dp else 20.dp),  // Adjust padding from top
                verticalAlignment = Alignment.CenterVertically,  // Align items vertically centered
                horizontalArrangement = Arrangement.SpaceBetween  // Align the text and buttons on opposite ends
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Machine Code:",
                        fontSize = if (isTablet) 24.sp else 16.sp,  // Adjust font size for tablet and phone
                        fontWeight = FontWeight.Bold, // Make the text bold
                        fontFamily = InriaSerif, // Apply the font family
                        modifier = Modifier.padding(bottom = 25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))  // Adjust the spacing between texts
                    Text(
                        text = machineCode,
                        fontSize = if (isTablet) 24.sp else 16.sp,  // Adjust font size for tablet and phone
                        fontWeight = FontWeight.Bold, // Make the text bold
                        fontFamily = InriaSerif, // Apply the font family
                        modifier = Modifier.padding(bottom = 25.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(if (isTablet) 32.dp else 16.dp)  // Adjust spacing between buttons
                ) {
                    CircleButton("Save", onClick = {
                        viewModel.onSaveClick()
                        // onNavigateToItemSelection()
                    }, isTablet)
                    CircleButton("Tare", onClick = { viewModel.onTareClick() }, isTablet)
                    CircleButton("View", onClick = { viewModel.onViewClick() }, isTablet)
                }

            }
        }
        // Centered weight display
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = netweight,  // Display time first
                    fontSize = if (isTablet) 40.sp else 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.GWGreen),
                    fontFamily = InriaSerif
                )
//                Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))
//                Text(
//                    text = time,  // Display weight below time
//                    fontSize = if (isTablet) 20.sp else 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = InriaSerif
//                )
//                Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))
//                Text(
//                    text = date,  // Display weight below time
//                    fontSize = if (isTablet) 20.sp else 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = InriaSerif
//                )
            }
        }


        // Display the popups based on ViewModel state
        if (isPopupVisible) {
            PopupScreen(
                popupData = popupData,
                onDismiss = { viewModel.onPopupClose() }
            )
        }

        // Trolley list popup (near the Tare button)
        if (isTrolleyPopupVisible) {
            TrolleyListPopup(
                trolleyList = trolleyList,
                onDismiss = { viewModel.onTrolleyPopupClose() },
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Align inside the Box
                    .padding(end = 29.dp, bottom = 125.dp)  // Adjust position near the Tare button
            )
        }
    }
}

@Composable
fun CircleButton(text: String, onClick: () -> Unit, isTablet: Boolean) {
    Button(
        onClick = onClick,  // This should be a regular lambda
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.GWGreen)
        ),
        modifier = Modifier.size(if (isTablet) 72.dp else 56.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontFamily = InriaSerif, // Apply the font family
            fontWeight = FontWeight.Bold, // Bold text
            fontSize = if (isTablet) 18.sp else 14.sp // Adjust text size for tablet and phone
        )
    }
}


@Preview
@Composable
fun PreviewWeighScaleScreen() {
    val bluetoothViewModel: BluetoothViewModel = viewModel()
    val mockMachineCode = "GWASSET001"// Add BluetoothViewModel instance
    WeighScaleScreen(
        machineCode = mockMachineCode,
        bluetoothViewModel = bluetoothViewModel,
        onNavigateToLogin = { /* Preview Action */ },
        onNavigateToItemSelection = { /* Preview Action */ }
    )
}