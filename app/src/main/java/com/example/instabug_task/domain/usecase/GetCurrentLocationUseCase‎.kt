package com.example.instabug_task.domain.usecase

import com.example.instabug_task.domain.repository.LocationRepository

class GetCurrentLocationUseCase(
    private val locationRepository: LocationRepository
) {
    fun execute(callback: (Result<String>) -> Unit) {
        locationRepository.getCurrentLocation { result ->
            callback(result)
        }
    }
}