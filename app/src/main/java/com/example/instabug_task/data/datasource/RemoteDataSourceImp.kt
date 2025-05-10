package com.example.instabug_task.data.datasource

import com.example.instabug_task.data.model.Response
import com.example.instabug_task.data.remote.WeatherAPIService
import com.example.instabug_task.data.utils.API_KEY
import com.example.instabug_task.data.utils.BASE_API_LINK
import com.example.instabug_task.data.utils.CONTENT_TYPE
import com.example.instabug_task.data.utils.getCurrentFormattedTime

class RemoteDataSource {
    fun getDataResponse(location: String, callback: (Response?) -> Unit) {
        val url = "$BASE_API_LINK$location?key=$API_KEY&contentType=$CONTENT_TYPE"
        WeatherAPIService.getResponse(url) { response ->
            response?.lastUpdate = getCurrentFormattedTime()
            callback(response)
        }
    }
}