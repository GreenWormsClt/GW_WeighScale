package com.example.gwweighscale.utils


import androidx.compose.ui.graphics.Color
import java.math.BigInteger
import java.security.MessageDigest

fun generateColorFromString(input: String): Color {
    // Use a hash function (MD5 in this case) to create a unique hash for the input
    val md5 = MessageDigest.getInstance("MD5").digest(input.toByteArray())
    val hash = BigInteger(1, md5).toString(16)

    // Use the hash to generate RGB values
    val red = Integer.parseInt(hash.substring(0, 2), 16) / 255f
    val green = Integer.parseInt(hash.substring(2, 4), 16) / 255f
    val blue = Integer.parseInt(hash.substring(4, 6), 16) / 255f

    // Return a Color object
    return Color(red, green, blue, 1f) // Full opacity
}
