package com.example.instabug_task.ui.veiwmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.usecase.GetWeatherDataUseCase

class WeatherViewModel(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {


    private val _weatherLiveData = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather> = _weatherLiveData

    fun getWeather() {
        getWeatherDataUseCase.getWeatherData { weather ->
            _weatherLiveData.postValue(weather)
        }
    }
}