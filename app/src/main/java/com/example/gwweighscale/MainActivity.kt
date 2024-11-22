package com.example.gwweighscale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import com.example.gwweighscale.Rooms.Entities.Tare


class MainActivity : ComponentActivity() {

    private val bluetoothViewModel: BluetoothViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val weighScaleViewModel: WeighScaleViewModel by viewModels()


    // Permissions launcher for Bluetooth
    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        bluetoothViewModel.updatePermissionsStatus(allGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bluetoothViewModel.checkBluetoothPermissions(this, permissionsLauncher)
        installSplashScreen()
        setContent {
            GWWeighScaleTheme {
                MyApp( bluetoothViewModel = bluetoothViewModel,loginViewModel = loginViewModel,weighScaleViewModel = weighScaleViewModel)
            }
        }
    }

}

@Composable
fun MyApp(
    bluetoothViewModel: BluetoothViewModel,
    loginViewModel: LoginViewModel,
    weighScaleViewModel: WeighScaleViewModel,
) {
    val navController = rememberNavController()
    WeighScaleApp(
        navController = navController,
        bluetoothViewModel = bluetoothViewModel,
        loginViewModel = loginViewModel,
        weighScaleViewModel = weighScaleViewModel

    )
}

@Composable
fun WeighScaleApp(
    navController: NavHostController,
    bluetoothViewModel: BluetoothViewModel,
    loginViewModel: LoginViewModel,
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
                onNavigateToItemSelection = { staffName, trolleyName, trolleyWeight, netWeight ->
                    // Navigate to ItemSelectionScreen with all required arguments
                    navController.navigate("item/$staffName/$trolleyName/$trolleyWeight/$netWeight")
                }
            )
        }
        // Item Selection Screen
        composable(
            route = "item/{staffName}/{trolleyName}/{trolleyWeight}/{netWeight}",
            arguments = listOf(
                navArgument("staffName") { type = NavType.StringType },
                navArgument("trolleyName") { type = NavType.StringType },
                navArgument("trolleyWeight") { type = NavType.StringType },
                navArgument("netWeight") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val staffName = backStackEntry.arguments?.getString("staffName") ?: "Unknown"
            val trolleyName = backStackEntry.arguments?.getString("trolleyName") ?: "Unknown"
            val trolleyWeight = backStackEntry.arguments?.getString("trolleyWeight") ?: "Unknown"
            val netWeight = backStackEntry.arguments?.getString("netWeight") ?: "Unknown"

            ItemSelectionScreen(
                onNavigateToWeighScale = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                staffName = staffName,
                trolleyName = trolleyName,
                trolleyWeight = trolleyWeight,
                netWeight = netWeight
            )
        }
    }
}
