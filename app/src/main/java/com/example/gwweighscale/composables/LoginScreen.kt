package com.example.gwweighscale.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.viewmodels.LoginViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.gwweighscale.R
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.fontfamily.InriaSerif
import com.example.gwweighscale.widgets.CustomTextField


@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), onLoginSuccess: () -> Unit) {

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white)) // Set background color to white
        ) {
            // Left Side with Logo
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    //.padding(bottom = 32.dp)
                    .background(colorResource(id = R.color.GWGreen)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize() , // Fill the entire screen
                    verticalArrangement = Arrangement.Center,  // Center the content vertically
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Add an Image at the top
                    Image(
                        painter = painterResource(id = R.drawable.splashscreen),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            // Right Side with Login Form
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        WindowInsets
                            .ime // Listen to IME (keyboard) insets
                            .asPaddingValues()
                    )
                    .background(colorResource(id = R.color.white)),

                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .verticalScroll(scrollState) // Enable scrolling for the entire Column
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 40.sp,
                        fontFamily = InriaSerif,  // Use InriaSerif font family
                        fontWeight = FontWeight.Bold,  // Use bold variant
                        color = colorResource(id = R.color.GWGreen)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = viewModel.loginModel.value.machineCode,
                        onValueChange = { newValue ->
                            val machinecodevalue = newValue.uppercase()
                            viewModel.loginModel.value =
                                viewModel.loginModel.value.copy(machineCode = machinecodevalue)
                        },
                        label = "Machine code",
                        isError = !viewModel.isMachineCodeValid.value,
                        errorMessage = viewModel.machineCodeErrorMessage.value,
                        borderColor = colorResource(id = R.color.GWGreen) ,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    CustomTextField(
                        value = viewModel.loginModel.value.password,
                        onValueChange = {
                            viewModel.loginModel.value =
                                viewModel.loginModel.value.copy(password = it)
                        },
                        label = "Password",
                        isPassword = true,
                        isError = !viewModel.isPasswordValid.value,
                        errorMessage = viewModel.passwordErrorMessage.value,
                        borderColor = colorResource(id = R.color.GWGreen), // Set border color to GWGreen
                        labelColor = colorResource(id = R.color.GWGreen),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )

//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    TextButton(
//                        onClick = { /* No action */ },
//                        modifier = Modifier.align(Alignment.End)
//                    ) {
//                        Text(
//                            text = "Forgot password?",
//                            fontFamily = InriaSerif,  // Use InriaSerif font family
//                            fontWeight = FontWeight.Normal,  // Use regular variant
//                            color = colorResource(id = R.color.GWGreen) // Set text color to GWGreen
//                        )
//                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { viewModel.onLoginClicked(onLoginSuccess) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.GWGreen),
                            contentColor = colorResource(id = R.color.white) // Always white text color
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Sign In",
                            fontFamily = InriaSerif,  // Use InriaSerif font family
                            fontWeight = FontWeight.Bold,  // Use bold variant
                            color = colorResource(id = R.color.white) // Explicitly set text color to white
                        )
                    }
                }
            }
        }
    }
}