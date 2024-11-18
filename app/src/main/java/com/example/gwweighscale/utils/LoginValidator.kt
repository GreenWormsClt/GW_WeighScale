package com.example.gwweighscale.utils

class LoginValidator {

    companion object {
        private const val VALID_MACHINE_CODE = "1"
        private const val VALID_PASSWORD = "2"
        private const val PASSWORD_LENGTH = 1

        // Validate Machine Code
        fun validateMachineCode(machineCode: String): Pair<Boolean, String?> {
            val sanitizedMachineCode = machineCode.filter { it.isUpperCase() || it.isDigit() }
            return when {
                sanitizedMachineCode.isEmpty() -> false to "Machine code is required."
                sanitizedMachineCode != VALID_MACHINE_CODE -> false to "Invalid machine code."
                else -> true to null
            }
        }

        // Validate Password
        fun validatePassword(password: String): Pair<Boolean, String?> {
            val sanitizedPassword = password.take(PASSWORD_LENGTH).filter { it.isLetterOrDigit() }
            return when {
                sanitizedPassword.isEmpty() -> false to "Password is required."
                sanitizedPassword.length != PASSWORD_LENGTH -> false to "Password must be exactly $PASSWORD_LENGTH characters."
                sanitizedPassword != VALID_PASSWORD -> false to "Invalid password."
                else -> true to null
            }

        }

        // Validate both Machine Code and Password
        fun validateLogin(machineCode: String, password: String): Pair<Boolean, String?> {
            val (isMachineCodeValid, machineCodeError) = validateMachineCode(machineCode)
            val (isPasswordValid, passwordError) = validatePassword(password)

            return when {
                !isMachineCodeValid -> false to machineCodeError
                !isPasswordValid -> false to passwordError
                else -> true to null
            }
        }
    }
}
