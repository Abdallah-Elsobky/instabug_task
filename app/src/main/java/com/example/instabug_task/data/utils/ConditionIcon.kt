package com.example.instabug_task.data.utils

import com.example.instabug_task.R


fun getWeatherIconRes(iconId: String?): Int {
    return when (iconId) {
        "clear-day" -> R.drawable.ic_sunny
        "clear-night" -> R.drawable.ic_sunny
        "partly-cloudy-day" -> R.drawable.partly_cloud
        "partly-cloudy-night" -> R.drawable.partly_cloud
        "cloudy" -> R.drawable.ic_cloudy
        "wind" -> R.drawable.ic_windy
        "fog" -> R.drawable.ic_foggy
        "rain" -> R.drawable.ic_rainny
        "showers-day" -> R.drawable.ic_shower_rain
        "showers-night" -> R.drawable.ic_shower_rain
        "thunder-rain" -> R.drawable.ic_thunder_rain
        "thunder-showers-day" -> R.drawable.ic_thunder_rain
        "thunder-showers-night" -> R.drawable.ic_thunder_rain
        "snow" -> R.drawable.ic_snow
        "snow-showers-day" -> R.drawable.ic_snow
        "snow-showers-night" -> R.drawable.ic_snow
        else -> R.drawable.ic_cloudy
    }
}

