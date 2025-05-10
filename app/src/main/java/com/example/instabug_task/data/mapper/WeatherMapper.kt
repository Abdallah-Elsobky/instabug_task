package com.example.instabug_task.data.mapper


import com.example.instabug_task.data.model.CurrentConditions
import com.example.instabug_task.data.model.DaysItem
import com.example.instabug_task.data.model.Response
import com.example.instabug_task.data.utils.fahrenheitToCelsius
import com.example.instabug_task.data.utils.getWeatherIconRes
import com.example.instabug_task.domain.model.DayConditions
import com.example.instabug_task.domain.model.DayWeather
import com.example.instabug_task.domain.model.Weather

class WeatherMapper {
    fun responseToWeather(response: Response): Weather {
        return Weather(
            address = response.address,
            description = response.description,
            timezone = response.timezone,
            days = response.days.map { dayToDayWeather(it) },
            currentConditions = currentConditionsToDayConditions(response.currentConditions)
        )
    }

    private fun dayToDayWeather(day: DaysItem): DayWeather {
        return DayWeather(
            datetime = day.datetime,
            datetimeEpoch = day.datetimeEpoch,
            tempmax = fahrenheitToCelsius(day.tempmax ?: 0.0),
            tempmin = fahrenheitToCelsius(day.tempmin ?: 0.0),
            temp = fahrenheitToCelsius(day.temp ?: 0.0),
            description = day.description,
            conditions = day.conditions,
            icon = getWeatherIconRes(day.icon)
        )
    }

    private fun currentConditionsToDayConditions(conditions: CurrentConditions?): DayConditions {
        return DayConditions(
            datetime = conditions?.datetime,
            datetimeEpoch = conditions?.datetimeEpoch,
            temp = fahrenheitToCelsius(conditions?.temp ?: 0.0),
            conditions = conditions?.conditions,
            icon = conditions?.icon
        )
    }
}