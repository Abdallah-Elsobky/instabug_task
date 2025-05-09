package com.example.instabug_task.data.datasourse


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.instabug_task.data.datasource.dataStore
import com.example.instabug_task.data.model.Response
import com.example.instabugtask.data.utils.JsonParser.parseWeatherResponse
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject


class LocalDataSource(private val context: Context) {

    private val KEY_CACHED_RESPONSE = stringPreferencesKey("cached_response")

    fun getCashedData(): Response = runBlocking {
        val prefs = context.dataStore.data.first()
        val json = prefs[KEY_CACHED_RESPONSE] ?: return@runBlocking Response()
        parseWeatherResponse(json)
    }

    fun updateCashedData(data: Response): Boolean = runBlocking {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("latitude", data.latitude)
            jsonObject.put("longitude", data.longitude)
            jsonObject.put("resolvedAddress", data.resolvedAddress)
            jsonObject.put("address", data.address)
            jsonObject.put("description", data.description)

            data.currentConditions?.let {
                val current = JSONObject()
                current.put("datetime", it.datetime)
                current.put("datetimeEpoch", it.datetimeEpoch)
                current.put("temp", it.temp)
                current.put("conditions", it.conditions)
                current.put("icon", it.icon)
                jsonObject.put("currentConditions", current)
            }

            val daysArray = JSONArray()
            data.days.forEach {
                val day = JSONObject()
                day.put("datetime", it.datetime)
                day.put("datetimeEpoch", it.datetimeEpoch)
                day.put("tempmax", it.tempmax)
                day.put("tempmin", it.tempmin)
                day.put("temp", it.temp)
                day.put("description", it.description)
                day.put("icon", it.icon)
                daysArray.put(day)
            }
            jsonObject.put("days", daysArray)

            context.dataStore.edit { preferences ->
                preferences[KEY_CACHED_RESPONSE] = jsonObject.toString()
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}