package com.example.gwweighscale.composables

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gwweighscale.widgets.CustomAppBar
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.R
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.gwweighscale.utils.ExcelExporter
import com.example.gwweighscale.viewmodels.BluetoothViewModel
import com.example.gwweighscale.viewmodels.TareViewModel
import android.view.WindowManager
import android.app.Activity
import kotlinx.coroutines.delay


@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighScaleScreen(
    machineCode: String,
    bluetoothViewModel: BluetoothViewModel,
    tareViewModel: TareViewModel = viewModel(),
    viewModel: WeighScaleViewModel = viewModel(),
    onNavigateToDeviceList: () -> Unit,
    onNavigateToStaffList: () -> Unit,
    onNavigateToLogin: () -> Unit, // Pass the navigation callback to LoginScreen
    onNavigateToItemSelection: (String, String, String, String, String) -> Unit,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth > 600.dp
    val scrollState = rememberScrollState()
    // val isTrolleyPopupVisible by tareViewModel.isTrolleyPopupVisible
    var calculatedWeight by remember { mutableStateOf<Double?>(null) }
    // val trolleyList by tareViewModel.allTares.observeAsState(emptyList())
    val weighScales = viewModel.allWeighScales.observeAsState(emptyList())
    val netWeight by bluetoothViewModel.netweight // Observe netWeight from BluetoothViewModel
    var selectedNetWeight by remember { mutableStateOf("") }
    val trolleyList by tareViewModel.allTares.observeAsState(emptyList())
    val selectedTrolley by tareViewModel.selectedTrolley

    var isTrolleyPopupVisible by remember { mutableStateOf(false) }
    // Accessing the ViewModel states
    val isPopupVisible by viewModel.isPopupVisible
    val popupData by viewModel.popupData
    val staff by viewModel.allStaffs.observeAsState(emptyList())
    // val isTrolleyPopupVisible by viewModel.isTrolleyPopupVisible
    var isReportVisible by remember { mutableStateOf(false) }

    val rfidMatch by viewModel.rfidMatch.observeAsState()
    var rfidTag by remember { mutableStateOf("") }
    var matchedStaffName by remember { mutableStateOf<String?>(null) }
    var staffId by remember { mutableStateOf<String?>(null) }
    // val tareList by viewModel.tareList.observeAsState(emptyList())


    val focusRequester = remember { FocusRequester() }
    val view = LocalView.current
    val netweight by bluetoothViewModel.netweight
    val time by bluetoothViewModel.time
    val context = LocalContext.current
    val date by bluetoothViewModel.date
    //var selectedTrolley by remember { mutableStateOf<Tare?>(null) }
    var isRfidFieldFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()

    var suppressKeyboard by remember { mutableStateOf(true) }

    // Add this DisposableEffect to hide the keyboard and manage focus
    DisposableEffect(Unit) {
        val activity = context as Activity
        // Hide keyboard and prevent it from showing
        activity.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )

        onDispose {
            // Reset the soft input mode when the composable is disposed
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED)
        }
    }

    LaunchedEffect(Unit) {
        // Ensure keyboard is hidden
        keyboardController?.hide()
        // Request focus but don't show keyboard
        focusRequester.requestFocus()
        bluetoothViewModel.connect()
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
                onTrolleyAdded = { trolley ->
                    tareViewModel.insertTare(trolley) // Add the trolley to the database
                },
                title = "GW Weigh Scale",
                iconRes = R.drawable.threedots,
                onNavigateToDeviceList = onNavigateToDeviceList,
                onLogoutClick = {
                    onNavigateToLogin() // Navigate to the Login screen
                },
                onAddStaff = { staff ->
                    viewModel.insertStaff(staff)
                },
                modifier = Modifier.zIndex(1f),
                onNavigateToStaffList = onNavigateToStaffList,
                onNavigateToLogin = onNavigateToLogin// Pass the callback for login navigation // Optional, you can pass a modifier if needed
            )

            Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left side: DATE and TIME
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "DATE : $date",
                        fontSize = if (isTablet) 20.sp else 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InriaSerif
                    )
                    Text(
                        text = "TIME : $time",
                        fontSize = if (isTablet) 20.sp else 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InriaSerif
                    )

                }
                selectedTrolley?.let { trolley ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "TrolleyName: ${trolley.name}",
                            fontSize = if (isTablet) 20.sp else 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InriaSerif
                        )
                        Text(
                            text = "TrolleyWeight: ${trolley.weight} KG",
                            fontSize = if (isTablet) 20.sp else 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InriaSerif
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            // RFID Input Field
            BasicTextField(
                value = rfidTag,
                onValueChange = { input ->
                    rfidTag = input
                    if (input.length == 10) {
                        // Process RFID immediately
                        val staffId = viewModel.validateRfidAndFetchStaffId(input)
                        val matchedStaffName = viewModel.validateRfidAndFetchStaffName(input)

                        if (matchedStaffName != null && selectedTrolley != null) {
                            // Navigate immediately without any delay
                            onNavigateToItemSelection(
                                matchedStaffName,
                                selectedTrolley!!.name,
                                selectedTrolley!!.weight.toString(),
                                netWeight,
                                staffId.toString()
                            )
                            rfidTag = "" // Clear the input after navigation
                        } else {
                            Toast.makeText(context, "RFID not found or trolley not selected!", Toast.LENGTH_SHORT).show()
                            rfidTag = "" // Clear the input on error
                        }
                    }
                },
                modifier = Modifier
                    .size(0.dp)  // Make it invisible but functional
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            // Immediately hide keyboard if it somehow appears
                            keyboardController?.hide()
                        }
                    }
                    .focusable(true), // Ensure it can receive focus
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                singleLine = true,
                decorationBox = {}
            )


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
                    CircleButton("Trolley", onClick = {  isTrolleyPopupVisible = true }, isTablet)
                    CircleButton("History", onClick = { viewModel.onViewClick() }, isTablet)
                    CircleButton(
                        "Report",
                        onClick = { viewModel.fetchSummaryDetails(); isReportVisible = true },
                        isTablet
                    )

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
            }
        }


        // Display the popups based on ViewModel state
        if (isPopupVisible) {
            // Observe reportDetails once in the composable scope
            val reportDetails = viewModel.reportDetails.observeAsState(emptyList()).value

            PopupScreen(
                reportDetails = reportDetails,
                onDismiss = { viewModel.onPopupClose() },
                exportToExcel = { context ->
                    ExcelExporter.exportReportData(
                        context = context,
                        reportData = reportDetails // Use the observed reportDetails here
                    )
                },
                context = context,
                resetItemReports = { viewModel.resetItemReports {} }
            )
        }
        if (isReportVisible) {
            ReportScreen(
                summaryDetails = viewModel.summaryDetails.observeAsState(emptyList()).value,
                onDismiss = { isReportVisible = false } // Close the report screen when dismissed
            )
        }
        if (isTrolleyPopupVisible) {
            TrolleyListPopup(
                trolleyList = trolleyList,
                onDismiss = {   isTrolleyPopupVisible = false   },
                onTrolleyDeleted = { trolley ->
                    tareViewModel.deleteTare(trolley)
                },
                onTrolleySelected = { selectedTrolleyItem ->
                    tareViewModel.selectTrolley(selectedTrolleyItem)
                    isTrolleyPopupVisible = false
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 32.dp)
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