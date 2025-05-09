package com.example.instabug_task.data.model

data class CurrentConditions(
	val icon: String? = null,
	val datetime: String? = null,
	val datetimeEpoch: Int? = null,
	val temp: Double? = null,
	val conditions: String? = null
)
