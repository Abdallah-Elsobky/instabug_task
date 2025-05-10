package com.example.instabug_task.data.utils

import kotlin.math.roundToInt

fun fahrenheitToCelsius(fahrenheit: Double): Double {
    return ((fahrenheit - 32) * 5 / 9 * 10).roundToInt() / 10.0
}

