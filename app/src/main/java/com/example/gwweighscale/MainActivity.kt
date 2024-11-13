package com.example.gwweighscale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gwweighscale.composables.ItemSelectionScreen
import com.example.gwweighscale.composables.LoginScreen
import com.example.gwweighscale.composables.WeighScaleScreen
import com.example.gwweighscale.ui.theme.GWWeighScaleTheme
import com.example.gwweighscale.viewmodels.BluetoothViewModel

class MainActivity : ComponentActivity() {

    private val bluetoothViewModel: BluetoothViewModel by viewModels()

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
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val bluetoothViewModel: BluetoothViewModel = viewModel()  // Instantiate BluetoothViewModel
    WeighScaleApp(navController = navController, bluetoothViewModel = bluetoothViewModel)
}

@Composable
fun WeighScaleApp(navController: NavHostController,bluetoothViewModel: BluetoothViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("home")
            })
        }
        composable("home") {
            WeighScaleScreen(
                bluetoothViewModel = bluetoothViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToItemSelection = {
                    navController.navigate("item")
                }
            )
        }
        composable("item") {
            ItemSelectionScreen(onNavigateToWeighScale = {
                navController.navigate("home")
            })
        }
    }

}


