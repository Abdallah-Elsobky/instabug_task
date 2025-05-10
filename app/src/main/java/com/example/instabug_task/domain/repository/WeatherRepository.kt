package com.example.instabug_task.domain.repository

import com.example.instabug_task.domain.model.Weather

interface WeatherRepository {
    fun getWeather(location: String, callback: (Weather?) -> Unit)
}