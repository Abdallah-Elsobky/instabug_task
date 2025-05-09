package com.example.instabugtask.data.remote

import com.example.instabug_task.data.model.Response
import com.example.instabugtask.data.utils.JsonParser.parseWeatherResponse
import java.net.HttpURLConnection
import java.net.URL

object WeatherAPIService {
    private fun sendGet(link: String): String {
        val url = URL(link)
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            return inputStream.bufferedReader().use { it.readText() }
        }
    }

    fun getResponse(url: String): Response {
        return parseWeatherResponse(sendGet(url))
    }
}