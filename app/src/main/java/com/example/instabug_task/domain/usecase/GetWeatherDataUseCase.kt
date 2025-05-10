package com.example.instabug_task.domain.usecase

import android.util.Log
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.repository.WeatherRepository

class GetWeatherDataUseCase(
    private val repository: WeatherRepository,
    private val getCurrentLocation: GetCurrentLocationUseCase
) {
    fun getWeatherData(callback: (Weather) -> Unit) {
        getCurrentLocation.execute { result ->
            result.onSuccess { location ->
                repository.getWeather(location) { weather ->
                    if (weather != null) {
                        Log.e("testo", "Weather data: ${weather.currentConditions?.conditions}")
                        callback(weather)
                    } else {
                        callback(Weather())
                    }
                }
            }.onFailure {
                callback(Weather())
            }
        }
    }
}