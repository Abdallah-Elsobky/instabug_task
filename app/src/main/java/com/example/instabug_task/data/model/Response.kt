package com.example.instabug_task.data.model

data class Response(
	val address: String? = null,
	val currentConditions: CurrentConditions? = null,
	val timezone: String? = null,
	val longitude: Any? = null,
	val latitude: Any? = null,
	val description: String? = null,
	val days: List<DaysItem> = listOf<DaysItem>(),
	val resolvedAddress: String? = null
)
