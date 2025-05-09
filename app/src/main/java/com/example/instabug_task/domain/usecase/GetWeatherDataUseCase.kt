package com.example.instabug_task.domain.usecase

import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.repository.WeatherRepository
import com.example.instabug_task.location.LocationHelper

//class GetWeatherDataUseCase(
//    private val repository: WeatherRepository,
//    private val getCurrentLocation: LocationHelper
//) {
//
//
//    private fun getWeatherData(): Weather {
//        return repository.getWeather(getCurrentLocation.getLocationString { })
//    }
//}