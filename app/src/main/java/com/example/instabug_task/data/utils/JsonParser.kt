package com.example.instabug_task.data.utils

import com.example.instabug_task.data.model.CurrentConditions
import com.example.instabug_task.data.model.DaysItem
import com.example.instabug_task.data.model.Response
import org.json.JSONArray
import org.json.JSONObject


object JsonParser{
    fun parseWeatherResponse(json: String): Response {
        val jsonObject = JSONObject(json)

        val latitude = jsonObject.optDouble("latitude")
        val longitude = jsonObject.optDouble("longitude")
        val resolvedAddress = jsonObject.optString("resolvedAddress")
        val address = jsonObject.optString("address")
        val description = jsonObject.optString("description")
        val timezone = jsonObject.optString("timezone")
        val currentConditionsJson = jsonObject.optJSONObject("currentConditions")
        val currentConditions = currentConditionsJson?.let {
            CurrentConditions(
                datetime = it.optString("datetime"),
                datetimeEpoch = it.optInt("datetimeEpoch"),
                temp = it.optDouble("temp"),
                conditions = it.optString("conditions"),
                icon = it.optString("icon")
            )
        }

        val daysJsonArray = jsonObject.optJSONArray("days") ?: JSONArray()
        val days = mutableListOf<DaysItem>()
        for (i in 0 until daysJsonArray.length()) {
            val dayObj = daysJsonArray.getJSONObject(i)
            days.add(
                DaysItem(
                    datetime = dayObj.optString("datetime"),
                    datetimeEpoch = dayObj.optInt("datetimeEpoch"),
                    tempmax = dayObj.optDouble("tempmax"),
                    tempmin = dayObj.optDouble("tempmin"),
                    temp = dayObj.optDouble("temp"),
                    description = dayObj.optString("description"),
                    conditions = dayObj.optString("conditions"),
                    icon = dayObj.optString("icon")
                )
            )
        }

        return Response(
            latitude = latitude,
            longitude = longitude,
            resolvedAddress = resolvedAddress,
            address = address,
            timezone = timezone,
            description = description,
            days = days,
            currentConditions = currentConditions
        )
    }
}