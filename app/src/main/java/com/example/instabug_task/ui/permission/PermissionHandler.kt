package com.example.instabug_task.ui.permission

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.instabug_task.ui.dialog.CustomDialog

class PermissionHandler(
    private val context: Context,
    private val requestLocationPermissionLauncher: ActivityResultLauncher<String>
) {

    private fun launchPermissionRequest() {
        requestLocationPermissionLauncher.launch(ACCESS_FINE_LOCATION)
    }

    fun requestLocationPermissionDialog() {
        val dialog = CustomDialog(
            context = context,
            title = "Location Permission",
            message = "We need to access the location to get the weather forecast",
            positiveText = "OK",
            negativeText = "Cancel",
            isCancelable = false,
            onPositive = {
                launchPermissionRequest()
            },
            onNegative = {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        )
        dialog.show()
    }

    fun showExplanationDialog() {
        val dialog = CustomDialog(
            context = context,
            title = "Permission Required",
            message = "Please enable location permission from settings to continue.",
            positiveText = "Setting",
            negativeText = "Cancel",
            isCancelable = false,
            onPositive = {
                openAppSettings(context)
            },
            onNegative = {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        )
        dialog.show()
    }

    private fun openAppSettings(context: Context) {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}