package com.example.instabug_task.data.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.example.instabug_task.ui.permission.PermissionHandler
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationHelper(
    private val context: Context,
    private val permissionHandler: PermissionHandler
) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var resolutionForResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    private var locationCallback: ((String?) -> Unit)? = null

    fun setResolutionLauncher(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        resolutionForResultLauncher = launcher
    }

    fun getLocationString(callback: (String?) -> Unit) {
        locationCallback = callback

        if (ActivityCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionHandler.requestLocationPermissionDialog()
            callback(null)
            return
        }
        checkLocationSettings()
    }

    private fun checkLocationSettings() {
        val locationRequest = createLocationRequest()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            fetchLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    resolutionForResultLauncher?.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("testo", e.message.toString())
                    locationCallback?.invoke(null)
                }
            } else {
                Toast.makeText(context, "GPS is disabled", Toast.LENGTH_SHORT).show()
                locationCallback?.invoke(null)
            }
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationCallback?.invoke(null)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLong = "${location.latitude},${location.longitude}"
                    locationCallback?.invoke(latLong)
                } else {
                    requestCurrentLocation()
                }
            }
            .addOnFailureListener {
                locationCallback?.invoke(null)
            }
    }

    @RequiresPermission(allOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    private fun requestCurrentLocation() {
        val locationRequest = createLocationRequest()
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                val latLong = location?.let { "${it.latitude},${it.longitude}" }
                locationCallback?.invoke(latLong)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, callback, null)
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()
    }
}
