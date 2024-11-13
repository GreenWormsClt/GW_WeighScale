package com.example.gwweighscale.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gwweighscale.models.LoginModel
import com.example.gwweighscale.utils.LoginValidator

class LoginViewModel : ViewModel() {
    var loginModel = mutableStateOf(LoginModel("", ""))
    var isMachineCodeValid = mutableStateOf(true)
    var isPasswordValid = mutableStateOf(true)
    var isCredentialsValid = mutableStateOf(true)

    var machineCodeErrorMessage = mutableStateOf<String?>(null)
    var passwordErrorMessage = mutableStateOf<String?>(null)

    fun validateFields(): Boolean {
        val (isMachineCodeValid, machineCodeError) = LoginValidator.validateMachineCode(loginModel.value.machineCode)
        val (isPasswordValid, passwordError) = LoginValidator.validatePassword(loginModel.value.password)

        this.isMachineCodeValid.value = isMachineCodeValid
        this.machineCodeErrorMessage.value = machineCodeError

        this.isPasswordValid.value = isPasswordValid
        this.passwordErrorMessage.value = passwordError

        return isMachineCodeValid && isPasswordValid
    }

    // Validate credentials using the specific machine code and password
    fun validateCredentials(): Boolean {
        val (isValid, errorMessage) = LoginValidator.validateLogin(
            machineCode = loginModel.value.machineCode,
            password = loginModel.value.password
        )
        isCredentialsValid.value = isValid

        // Display appropriate error message
        if (!isValid) {
            machineCodeErrorMessage.value = errorMessage
            passwordErrorMessage.value = errorMessage
        }

        return isValid
    }

    fun onLoginClicked(onSuccess: () -> Unit) {
        if (validateFields()) {
            if (validateCredentials()) {
                onSuccess()
            } else {

            }
        }
    }
}
