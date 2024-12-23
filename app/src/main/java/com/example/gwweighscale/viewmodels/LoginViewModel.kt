//package com.example.gwweighscale.viewmodels
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.gwweighscale.models.LoginModel
//import com.example.gwweighscale.utils.LoginValidator
//

package com.example.gwweighscale.viewmodels
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.gwweighscale.models.LoginModel
import com.example.gwweighscale.rooms.dao.CredentialDao
import com.example.gwweighscale.rooms.database.AppDatabase
import com.example.gwweighscale.utils.LoginValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.gwweighscale.rooms.entities.Credential

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val credentialDao: CredentialDao
    var loginModel = mutableStateOf(LoginModel("", ""))
    var isMachineCodeValid = mutableStateOf(true)
    var isPasswordValid = mutableStateOf(true)
    var machineCodeErrorMessage = mutableStateOf<String?>(null)
    var passwordErrorMessage = mutableStateOf<String?>(null)
    private val _credentials = MutableStateFlow<List<Credential>>(emptyList())
    val credentials: StateFlow<List<Credential>> = _credentials

    init {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "app_database").build()
        credentialDao = db.credentialDao()
        loadCredentials()
        // Initialize loginModel with saved credentials if available
    }
    private fun loadCredentials() {
        viewModelScope.launch {
            _credentials.value = credentialDao.getAllCredentials().first()
        }
    }

    fun onLoginClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val credential = credentialDao.validateCredentials(
                machineCode = loginModel.value.machineCode,
                password = loginModel.value.password
            )
            if (credential != null) {
                // Valid credentials
                isMachineCodeValid.value = true
                isPasswordValid.value = true
                onSuccess()
            } else {
                // Invalid credentials
                isMachineCodeValid.value = false
                isPasswordValid.value = false
                machineCodeErrorMessage.value = "Invalid machine code or password."
                passwordErrorMessage.value = "Invalid machine code or password."
            }
        }
    }

    suspend fun saveCredentials(machineCode: String, password: String) {
        val credential = Credential(machineCode = machineCode, password = password)
        credentialDao.insertCredential(credential)
    }
    fun addCredential(machineCode: String, password: String) {
        viewModelScope.launch {
            val credential = Credential(
                machineCode = machineCode,
                password = password
            )
            credentialDao.insertCredential(credential)
            loadCredentials()
        }
    }
    fun deleteCredential(credential: Credential) {
        viewModelScope.launch {
            try {
                credentialDao.deleteCredential(credential) // Correct DAO function
                loadCredentials() // Refresh UI with updated credentials
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.gwweighscale.models.LoginModel
//import com.example.gwweighscale.utils.LoginValidator
//
//class LoginViewModel : ViewModel() {
//    var loginModel = mutableStateOf(LoginModel("", ""))
//    var isMachineCodeValid = mutableStateOf(true)
//    var isPasswordValid = mutableStateOf(true)
//    var isCredentialsValid = mutableStateOf(true)
//
//    var machineCodeErrorMessage = mutableStateOf<String?>(null)
//    var passwordErrorMessage = mutableStateOf<String?>(null)
//
//    fun validateFields(): Boolean {
//        val (isMachineCodeValid, machineCodeError) = LoginValidator.validateMachineCode(loginModel.value.machineCode)
//        val (isPasswordValid, passwordError) = LoginValidator.validatePassword(loginModel.value.password)
//
//        this.isMachineCodeValid.value = isMachineCodeValid
//        this.machineCodeErrorMessage.value = machineCodeError
//
//        this.isPasswordValid.value = isPasswordValid
//        this.passwordErrorMessage.value = passwordError
//
//        return isMachineCodeValid && isPasswordValid
//    }
//
//    // Validate credentials using the specific machine code and password
//    fun validateCredentials(): Boolean {
//        val (isValid, errorMessage) = LoginValidator.validateLogin(
//            machineCode = loginModel.value.machineCode,
//            password = loginModel.value.password
//        )
//        isCredentialsValid.value = isValid
//
//        // Display appropriate error message
//        if (!isValid) {
//            machineCodeErrorMessage.value = errorMessage
//            passwordErrorMessage.value = errorMessage
//        }
//
//        return isValid
//    }
//
//    fun onLoginClicked(onSuccess: () -> Unit) {
//        if (validateFields()) {
//            if (validateCredentials()) {
//                onSuccess()
//            } else {
//
//            }
//        }
//    }
//}
//
////class LoginViewModel : ViewModel() {
////    var loginModel = mutableStateOf(LoginModel("", ""))
////    var isMachineCodeValid = mutableStateOf(true)
////    var isPasswordValid = mutableStateOf(true)
////    var isCredentialsValid = mutableStateOf(true)
////
////    var machineCodeErrorMessage = mutableStateOf<String?>(null)
////    var passwordErrorMessage = mutableStateOf<String?>(null)
////
////    fun validateFields(): Boolean {
////        val (isMachineCodeValid, machineCodeError) = LoginValidator.validateMachineCode(loginModel.value.machineCode)
////        val (isPasswordValid, passwordError) = LoginValidator.validatePassword(loginModel.value.password)
////
////        this.isMachineCodeValid.value = isMachineCodeValid
////        this.machineCodeErrorMessage.value = machineCodeError
////
////        this.isPasswordValid.value = isPasswordValid
////        this.passwordErrorMessage.value = passwordError
////
////        return isMachineCodeValid && isPasswordValid
////    }
////
////    // Validate credentials using the specific machine code and password
////    fun validateCredentials(): Boolean {
////        val (isValid, errorMessage) = LoginValidator.validateLogin(
////            machineCode = loginModel.value.machineCode,
////            password = loginModel.value.password
////        )
////        isCredentialsValid.value = isValid
////
////        // Display appropriate error message
////        if (!isValid) {
////            machineCodeErrorMessage.value = errorMessage
////            passwordErrorMessage.value = errorMessage
////        }
////
////        return isValid
////    }
////
////    fun onLoginClicked(onSuccess: () -> Unit) {
////        if (validateFields()) {
////            if (validateCredentials()) {
////                onSuccess()
////            } else {
////
////            }
////        }
////    }
////}
