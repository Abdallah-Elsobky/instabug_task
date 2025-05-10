package com.example.instabug_task.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentFormattedTime(): String {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(Date())
}