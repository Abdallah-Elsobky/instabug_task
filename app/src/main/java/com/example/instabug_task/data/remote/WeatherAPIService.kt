package com.example.instabug_task.data.remote

import com.example.instabug_task.data.model.Response
import com.example.instabug_task.data.utils.JsonParser.parseWeatherResponse
import java.net.HttpURLConnection
import java.net.URL

object WeatherAPIService {

    fun getResponse(url: String, callback: (Response?) -> Unit) {
        Thread {
            try {
                val responseText = sendGet(url)
                val response = parseWeatherResponse(responseText)
                callback(response)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(null)
            }
        }.start()
    }

    private fun sendGet(link: String): String {
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return connection.inputStream.bufferedReader().use { it.readText() }
    }
}