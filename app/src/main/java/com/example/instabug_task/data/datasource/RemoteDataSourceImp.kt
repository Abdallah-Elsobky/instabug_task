package com.example.instabugtask.data.datasource

import com.example.instabug_task.data.model.Response
import com.example.instabug_task.data.utils.API_KEY
import com.example.instabug_task.data.utils.BASE_API_LINK
import com.example.instabug_task.data.utils.CONTENT_TYPE
import com.example.instabugtask.data.remote.WeatherAPIService

class RemoteDataSource  {
    fun getDataResponse(location : String): Response {
        return WeatherAPIService.getResponse("$BASE_API_LINK$location?key=$API_KEY&contentType=$CONTENT_TYPE")
    }
}