package com.example.instabug_task.data.repository

import android.util.Log
import com.example.instabug_task.data.datasourse.LocalDataSource
import com.example.instabug_task.data.mapper.WeatherMapper
import com.example.instabug_task.data.utils.NetworkChecker
import com.example.instabug_task.domain.model.Weather
import com.example.instabug_task.domain.repository.WeatherRepository
import com.example.instabugtask.data.datasource.RemoteDataSource

class WeatherRepositoryImp(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val weatherMapper: WeatherMapper,
    private val networkChecker: NetworkChecker,
) : WeatherRepository {
    override fun getWeather(location: String?): Weather {

            return if (location != null && networkChecker.isNetworkAvailable()) {
                Log.e("testo", "remote: $location")
                try {
                    val remoteResponse = remoteDataSource.getDataResponse(location)
                    localDataSource.updateCashedData(remoteResponse)
                    weatherMapper.responseToWeather(remoteResponse)
                }catch (e:Exception){
                    Log.e("testo", e.message.toString())
                }
                weatherMapper.responseToWeather(localDataSource.getCashedData())
            } else {
                Log.e("testo", "local: $location")
                val localResponse = localDataSource.getCashedData()
                weatherMapper.responseToWeather(localResponse)
            }

    }
}