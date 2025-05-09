package com.example.instabug_task.domain.model

data class Weather(
    var address: String? = null,
    var description: String? = null,
    val timezone: String? = null,
    var days: List<DayWeather> = emptyList(),
    var currentConditions: DayConditions? = DayConditions()
)
