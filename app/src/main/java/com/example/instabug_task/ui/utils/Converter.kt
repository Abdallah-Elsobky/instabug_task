package com.example.instabug_task.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeekFromDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val date = LocalDate.parse(dateString, formatter)
    return date.dayOfWeek.name.take(3).capitalize()
}

