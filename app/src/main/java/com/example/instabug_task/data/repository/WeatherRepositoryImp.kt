package com.example.instabug_task.data.repository

import android.util.Log
import com.example.instabug_task.data.datasource.LocalDataSource
import com.example.instabug_task.data.datasource.RemoteDataSource
import com.example.instabug_task.data.mapper.WeatherMapper
import com.example.instabug_task.data.utils.NetworkChecker
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.repository.WeatherRepository

class WeatherRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val weatherMapper: WeatherMapper,
    private val networkChecker: NetworkChecker
) : WeatherRepository {

    override fun getWeather(location: String, callback: (Weather?) -> Unit) {
        if (networkChecker.isNetworkAvailable()) {
            remoteDataSource.getDataResponse(location) { remoteResponse ->
                if (remoteResponse != null) {
                    Log.e("Testo",location)
                    localDataSource.updateCashedData(remoteResponse)
                    val weather = weatherMapper.responseToWeather(remoteResponse)
                    callback(weather)
                } else {
                    val localResponse = localDataSource.getCashedData()
                    val weather = weatherMapper.responseToWeather(localResponse)
                    callback(weather)
                }
            }
        } else {
            val localResponse = localDataSource.getCashedData()
            val weather = weatherMapper.responseToWeather(localResponse)
            callback(weather)
        }
    }
}