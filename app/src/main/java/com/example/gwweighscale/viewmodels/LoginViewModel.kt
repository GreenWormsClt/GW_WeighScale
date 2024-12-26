
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
