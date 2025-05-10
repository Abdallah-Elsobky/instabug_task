package com.example.instabug_task.data.repository

import android.util.Log
import com.example.instabug_task.data.location.LocationHelper
import com.example.instabug_task.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationHelper: LocationHelper
) : LocationRepository {

    override fun getCurrentLocation(callback: (Result<String>) -> Unit) {
        locationHelper.getLocationString { location ->
            Log.i("testo", location.toString())
            if (location != null) {
                callback(Result.success(location))
            } else {
                callback(Result.failure(Exception("Location not available")))
            }
        }
    }
}