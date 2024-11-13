package com.example.gwweighscale.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gwweighscale.viewmodels.LoginViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.gwweighscale.R
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.unit.sp
import com.example.gwweighscale.widgets.CustomTextField


@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), onLoginSuccess: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Left Side with Logo
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(bottom = 32.dp)
                    .background(colorResource(id = R.color.GWGreen)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),  // Fill the entire screen
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
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 40.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colorResource(id = R.color.GWGreen)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = viewModel.loginModel.value.machineCode,
                        onValueChange = {
                            viewModel.loginModel.value =
                                viewModel.loginModel.value.copy(machineCode = it)
                        },
                        label = "Machine code",
                        isError = !viewModel.isMachineCodeValid.value,
                        errorMessage = viewModel.machineCodeErrorMessage.value
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = viewModel.loginModel.value.password,
                        onValueChange = {
                            viewModel.loginModel.value =
                                viewModel.loginModel.value.copy(password = it)
                        },
                        label = "Password",
                        isPassword = true,
                        isError = !viewModel.isPasswordValid.value,
                        errorMessage = viewModel.passwordErrorMessage.value
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { /* No action */ },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot password?")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.onLoginClicked(onLoginSuccess) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.GWGreen),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Sign In")
                    }
                }
            }
        }
    }
}
