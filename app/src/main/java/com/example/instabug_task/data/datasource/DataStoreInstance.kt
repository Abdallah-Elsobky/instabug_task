package com.example.instabug_task.data.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "weather_cache")