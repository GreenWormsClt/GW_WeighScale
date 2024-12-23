package com.example.gwweighscale.utils

class LoginValidator {
    companion object {

    fun validateMachineCode(machineCode: String, validMachineCode: String): Pair<Boolean, String?> {
        return when {
            machineCode.isEmpty() -> false to "Machine code is required."
            machineCode != validMachineCode -> false to "Invalid machine code."
            else -> true to null
        }
    }

    fun validatePassword(password: String, validPassword: String): Pair<Boolean, String?> {
        return when {
            password.isEmpty() -> false to "Password is required."
            password != validPassword -> false to "Invalid password."
            else -> true to null
        }
    }

    fun validateLogin(machineCode: String, password: String, validMachineCode: String, validPassword: String): Pair<Boolean, String?> {
        val (isMachineCodeValid, machineCodeError) = validateMachineCode(machineCode, validMachineCode)
        val (isPasswordValid, passwordError) = validatePassword(password, validPassword)

        return when {
            !isMachineCodeValid -> false to machineCodeError
            !isPasswordValid -> false to passwordError
            else -> true to null
        }
    }

    }

}
