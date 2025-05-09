package com.example.instabug_task.domain.model

data class DayConditions(
    var datetime: String? = null,
    var datetimeEpoch: Int? = null,
    var temp: Double? = null,
    var conditions: String? = null,
    var icon: String? = null,
)