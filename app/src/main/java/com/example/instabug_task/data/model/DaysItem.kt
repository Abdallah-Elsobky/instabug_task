package com.example.instabug_task.data.model

data class DaysItem(
	val icon: String? = null,
	val description: String? = null,
	val datetime: String? = null,
	val datetimeEpoch: Int? = null,
	val tempmin: Double? = null,
	val temp: Double? = null,
	val tempmax: Double? = null,
	val conditions: String? = null
)
