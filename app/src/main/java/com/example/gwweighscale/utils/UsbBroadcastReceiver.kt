package com.example.gwweighscale.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.app.PendingIntent
import android.util.Log

class UsbBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (device != null) {
                    // Handle the USB device attached event
                    handleUsbDevice(context, device)
                }
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (device != null) {
                    // Handle the USB device detached event
                    Log.d("UsbBroadcastReceiver", "USB device detached: ${device.deviceName}")
                }
            }
        }
    }

    private fun handleUsbDevice(context: Context, device: UsbDevice) {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val permissionIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(ACTION_USB_PERMISSION),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE for Android 12+
        )
        usbManager.requestPermission(device, permissionIntent)
        Log.d("UsbBroadcastReceiver", "USB device attached: ${device.deviceName}")
    }


    companion object {
        const val ACTION_USB_PERMISSION = "com.example.gwweighscale.USB_PERMISSION"
    }
}
