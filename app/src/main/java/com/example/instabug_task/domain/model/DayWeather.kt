package com.example.instabug_task.domain.model

data class DayWeather(
    var datetime: String? = null,
    var datetimeEpoch: Int? = null,
    var tempmax: Double? = null,
    var tempmin: Double? = null,
    var temp: Double? = null,
    var description: String? = null,
    var icon: String? = null,
)
