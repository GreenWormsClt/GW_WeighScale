package com.example.gwweighscale

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.gwweighscale.Repository.StaffRepository
import com.example.gwweighscale.composables.ItemSelectionScreen
import com.example.gwweighscale.composables.LoginScreen
import com.example.gwweighscale.composables.WeighScaleScreen
import com.example.gwweighscale.ui.theme.GWWeighScaleTheme
import com.example.gwweighscale.viewmodels.BluetoothViewModel
import com.example.gwweighscale.viewmodels.LoginViewModel
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import androidx.navigation.navArgument
import com.example.gwweighscale.composables.BluetoothDeviceListScreen
import com.example.gwweighscale.viewmodels.BluetoothtestViewmodel
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : ComponentActivity() {

    private val bluetoothViewModel: BluetoothViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val weighScaleViewModel: WeighScaleViewModel by viewModels()
    private val bluetoothtestViewmodel: BluetoothtestViewmodel by viewModels()


    //  Permissions launcher for Bluetooth
    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        bluetoothViewModel.updatePermissionsStatus(allGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        bluetoothViewModel.checkBluetoothPermissions(this, permissionsLauncher)
        //installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContent {
            GWWeighScaleTheme (useDarkTheme = false){
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
                    val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                    view.updatePadding(bottom = bottom)
                    insets

                }
                MyApp(
                    bluetoothViewModel = bluetoothViewModel,
                    loginViewModel = loginViewModel,
                    weighScaleViewModel = weighScaleViewModel,
                    bluetoothtestViewmodel = bluetoothtestViewmodel
                )
            }
        }
    }

}

@Composable
fun MyApp(
    bluetoothViewModel: BluetoothViewModel,
    loginViewModel: LoginViewModel,
    weighScaleViewModel: WeighScaleViewModel,
    bluetoothtestViewmodel: BluetoothtestViewmodel
) {
    val navController = rememberNavController()
    WeighScaleApp(
        navController = navController,
        bluetoothViewModel = bluetoothViewModel,
        loginViewModel = loginViewModel,
        weighScaleViewModel = weighScaleViewModel,
        bluetoothtestViewmodel = bluetoothtestViewmodel

    )
}

@Composable
fun WeighScaleApp(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel,
    loginViewModel: LoginViewModel,
    bluetoothtestViewmodel: BluetoothtestViewmodel,
    weighScaleViewModel: WeighScaleViewModel
) {
    val machineCode = loginViewModel.loginModel.value.machineCode

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("home")
                })
        }
        composable("home") {
            WeighScaleScreen(
                machineCode = machineCode,
                bluetoothViewModel = bluetoothViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onNavigateToItemSelection = { staffName, staffId, trolleyName, trolleyWeight, netWeight ->
                    // Navigate to ItemSelectionScreen with all required arguments
                    navController.navigate("item/$staffName/$staffId/$trolleyName/$trolleyWeight/$netWeight")
                },
                onNavigateToDeviceList = {
                    navController.navigate("bluetooth_list")
                }
            )
        }
        // Item Selection Screen
        composable(
            route = "item/{staffName}/{trolleyName}/{trolleyWeight}/{netWeight}/{staffId}",
            arguments = listOf(
                navArgument("staffName") { type = NavType.StringType },
                navArgument("trolleyName") { type = NavType.StringType },
                navArgument("trolleyWeight") { type = NavType.StringType },
                navArgument("netWeight") { type = NavType.StringType },
                navArgument("staffId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val staffName = backStackEntry.arguments?.getString("staffName") ?: "Unknown"
            val trolleyName = backStackEntry.arguments?.getString("trolleyName") ?: "Unknown"
            val trolleyWeight = backStackEntry.arguments?.getString("trolleyWeight") ?: "Unknown"
            val netWeight = backStackEntry.arguments?.getString("netWeight") ?: "Unknown"
            val staffId = backStackEntry.arguments?.getInt("staffId") ?: 0

            ItemSelectionScreen(
                onNavigateToWeighScale = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                staffName = staffName,
                trolleyName = trolleyName,
                trolleyWeight = trolleyWeight,
                netWeight = netWeight,
                staffId = staffId
            )
        }
        // Bluetooth Device List Screen
        composable("bluetooth_list") {
            BluetoothDeviceListScreen(
                bluetoothtestViewModel = bluetoothtestViewmodel,
                bluetoothViewModel = bluetoothViewModel,
                onBackClick = { navController.popBackStack() },
                onDeviceSelected = {
                    navController.popBackStack() // Return to the previous screen
                    bluetoothViewModel.connect() // Attempt connection after device selection
                }
            )
        }
    }
}
