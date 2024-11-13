package com.example.gwweighscale.composables

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gwweighscale.R
import com.example.gwweighscale.models.PopupData
import com.example.gwweighscale.viewmodels.WeighScaleViewModel
import com.example.gwweighscale.widgets.CircleButton
import com.example.gwweighscale.widgets.CustomAppBar

// UI Composable

@Composable
fun WeighScaleScreen(viewModel: WeighScaleViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isTablet = screenWidth > 600.dp

    val isPopupVisible by viewModel.isPopupVisible
    val popupData by viewModel.popupData


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isTablet) 42.dp else 16.dp)  // Adjust padding for tablet and phone
    ) {

        CustomAppBar(
            title = "GW Weigh Scale",
            iconRes = R.drawable.threedots,
            onLogoutClick = { navigateToLogin() },
            onExitClick = { (LocalContext.current as? Activity)?.finish() }
        )

        Spacer(modifier = Modifier.height(if (isTablet) 32.dp else 16.dp))

        // Image below 3 dots (now inside AppBar)
        Box(
            modifier = Modifier
                .size(if (isTablet) 150.dp else 75.dp)  // Adjust image size for tablet and phone
                .align(Alignment.End)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gwicon),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Centered weight display
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewModel.weight.value,
                fontSize = if (isTablet) 96.sp else 48.sp,  // Adjust font size for weight display
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "KG",
                fontSize = if (isTablet) 96.sp else 48.sp,  // Adjust font size for unit display
                fontWeight = FontWeight.Bold
            )
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
                    modifier = Modifier.padding(bottom = 25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))  // Adjust the spacing between texts
                Text(
                    text = "GWASSET001",
                    fontSize = if (isTablet) 24.sp else 16.sp,  // Adjust font size for tablet and phone
                    fontWeight = FontWeight.Bold, // Make the text bold
                    modifier = Modifier.padding(bottom = 25.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(if (isTablet) 32.dp else 16.dp)  // Adjust spacing between buttons
            ) {
                CircleButton("Save", onClick = { viewModel.onSaveClick() }, isTablet)
                CircleButton("Tare", onClick = { viewModel.onTareClick() }, isTablet)
                CircleButton("View", onClick = { viewModel.onViewClick() }, isTablet)
            }
        }
    }
    // Show popup if visible
    if (isPopupVisible) {
        PopupScreen(
            popupData = popupData,
            onDismiss = { viewModel.onPopupClose() }
        )
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "weighScaleScreen") {
        composable("weighScaleScreen") {
            WeighScaleScreen(
                navigateToLogin = { navController.navigate("loginScreen") }
            )
        }
        composable("loginScreen") {
            LoginScreen(onLoginSuccess = { navController.navigate("weighScaleScreen") })
        }
    }
}


@Composable
fun CircleButton(text: String, onClick: () -> Unit, isTablet: Boolean) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(if (isTablet) 72.dp else 56.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun PreviewWeighScaleScreen() {
    WeighScaleScreen()
}