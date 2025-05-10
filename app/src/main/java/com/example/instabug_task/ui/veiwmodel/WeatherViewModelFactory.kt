package com.example.instabug_task.ui.veiwmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instabug_task.domain.usecase.GetWeatherDataUseCase

class WeatherViewModelFactory(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(getWeatherDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}