package com.example.instabug_task.domain.repository

interface LocationRepository {
    fun getCurrentLocation(callback: (Result<String>) -> Unit)
}